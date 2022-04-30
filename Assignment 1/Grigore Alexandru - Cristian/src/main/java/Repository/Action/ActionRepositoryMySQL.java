package Repository.Action;

import Model.Action;
import Model.Builder.ActionBuilder;
import Model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActionRepositoryMySQL implements ActionRepository{

    private final Connection connection;
    public ActionRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Action> findAll() {
        List<Action> actions = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from employee_actions";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                actions.add(getActionsFromResultSet(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return actions;
    }

    @Override
    public Optional<Action> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean save(Action action) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO employee_actions values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, action.getId());
            insertUserStatement.setString(2, action.getActivity());
            insertUserStatement.setDate(3, new java.sql.Date(action.getActionDate().getTime()));
            insertUserStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from employee_actions where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Action getActionsFromResultSet(ResultSet rs) throws  SQLException{
        return new ActionBuilder().setId(rs.getLong("id"))
                .setActivity(rs.getString("action"))
                .setActionDate(rs.getDate("date"))
                .build();
    }
}
