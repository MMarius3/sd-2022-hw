package Service.User;

import Database.JDBConnectionWrapper;
import Model.Action;
import Model.User;
import Repository.Security.RightRolesRepositoryMySQL;
import Repository.User.UserRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static Database.Constants.Schemas.PRODUCTION;

public class AdminServiceMySQL implements AdminService{

    private final UserRepository userRepository;

    public AdminServiceMySQL(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        Predicate<User> byRole = employee -> new RightRolesRepositoryMySQL(new JDBConnectionWrapper(PRODUCTION).getConnection()).findRolesForUser(employee.getId()).getRole().equals("employee");
        return userRepository.findAll().stream().filter(byRole).collect(Collectors.toList());
    }

    @Override
    public void addEmployee(User user) {
        userRepository.save(user);
    }

    @Override
    public void editEmployee(User user) {
        Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        try{
            Statement statement = connection.createStatement();
            String update = "UPDATE user" +
                    " SET  id ="  + user.getId() +
                    ", username = '"+ user.getUsername() +
                    "', password= '"+user.getPassword() +
                    "' WHERE id = " + user.getId() + ";";
            statement.executeUpdate(update);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(User user) {
        Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        try{
            Statement statement = connection.createStatement();
            String delete = "DELETE FROM user WHERE id = " + user.getId() + ";";
            statement.executeUpdate(delete);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void generateReport(User user, Date start, Date end, List<Action> actions) {
        List<Action> filtered = actions.stream().filter(action -> action.getActionDate().compareTo(start) > 0 && action.getActionDate().compareTo(end) < 0).collect(Collectors.toList());
        List<Action> filteredByUser = filtered.stream().filter(action -> action.getId() == user.getId()).collect(Collectors.toList());
        try {
            FileWriter report = new FileWriter("report.txt");
            for(Action a: filteredByUser){
                report.write(a.getActivity() + " " +user.getUsername() + " " + a.getActionDate().toString() + "\n");
            }
            report.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
