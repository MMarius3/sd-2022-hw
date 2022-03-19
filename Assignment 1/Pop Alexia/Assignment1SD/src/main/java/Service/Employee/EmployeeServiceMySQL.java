package Service.Employee;

import Model.User;
import Repository.User.UserRepository;
import javafx.collections.ObservableList;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class EmployeeServiceMySQL implements EmployeeService {

    private final UserRepository userRepository;

    public EmployeeServiceMySQL(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean updateEmp(Long id,String username,String password) {
        String encodedPassword = "";
        if(!password.equals("")) encodedPassword = encodePassword(password);
        return userRepository.update(id,username,encodedPassword);
    }

    @Override
    public boolean deleteEmp(String username) {
        return userRepository.delete(username);
    }

    @Override
    public ObservableList<User> viewEmp() {
        return userRepository.findAll();
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
