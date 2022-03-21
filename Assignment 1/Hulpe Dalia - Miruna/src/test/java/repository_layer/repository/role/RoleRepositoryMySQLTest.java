package repository_layer.repository.role;

import bussiness_layer.builder.RoleBuilder;
import bussiness_layer.models.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository_layer.database_builder.DBConnectionFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleRepositoryMySQLTest {

    private static RoleRepository roleRepository;

    @BeforeAll
    public static void setupClass() {

        roleRepository = new RoleRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection());
    }

    @Test
    void insert() {
        Role role = new RoleBuilder()
                .setRole("Test_Role")
                .build();
        roleRepository.insert(role);

    }

    @Test
    void findByName() {
        Role role = roleRepository.findByName("Test_Role");
        assertEquals(java.util.Optional.ofNullable(role.getId()), java.util.Optional.ofNullable(4l));
    }

    @Test
    void findById() {
        Role role = roleRepository.findById(4l);
        assertEquals(java.util.Optional.ofNullable(role.getRole()), java.util.Optional.ofNullable("Test_Role"));
    }
}