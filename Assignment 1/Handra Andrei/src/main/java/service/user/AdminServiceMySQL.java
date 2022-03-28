package service.user;

import model.Action;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import static database.Constants.Roles.EMPLOYEE;

public class AdminServiceMySQL implements AdminService{


    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AdminServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public boolean createEmployee(String username, String password) {
        String encodedPassword = encodePassword(password);

        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(encodedPassword)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        List<User> employees = new ArrayList<>();
        for(User user:users){
            List<Role> roles = rightsRolesRepository.findRolesForUser(user.getId());
            if(roles.get(0).getRole().equals(EMPLOYEE)){
                employees.add(user);
            }
        }
        return employees;
    }

    @Override
    public boolean updateEmployee(Long id,String username, String password) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            if(!username.isEmpty()){
                user.get().setUsername(username);
            }
            if(!password.isEmpty()){
                user.get().setPassword(encodePassword(password));
            }
            return userRepository.update(user.get());
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        return userRepository.delete(id);
    }

    @Override
    public List<Action> generateReport(Long id, Date date1, Date date2) {
        List<Action> actions = rightsRolesRepository.generateReport(id);
        List<Action> newActions = new ArrayList<>();
        for(Action action:actions){
            if(action.getActionDate().compareTo(date1) <= 0 && action.getActionDate().compareTo(date2) >= 0){
                newActions.add(action);
            }
        }
        return newActions;
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
