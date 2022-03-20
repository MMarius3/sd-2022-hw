package service;

import mapper.UserMapper;
import org.junit.jupiter.api.Test;
import mapper.UserActionMapper;
import repository.user.UserRepositoryMySQL;
import repository.user_action.UserActionRepositoryMySQL;
import service.generate_report.PDFReportGenerator;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenerateReportTest {

//    @BeforeAll
//    public static void refreshDB() {
//        DatabaseInitializer.setConnection(DatabaseConnectionFactory.getConnection(true));
//        DatabaseInitializer.refreshDB();
//    }

    @Test
    public void generatePDF() {

        PDFReportGenerator reportGenerator =
                new PDFReportGenerator(new UserActionRepositoryMySQL(), new UserActionMapper().getMapper());

        reportGenerator
                .setUserRepository(new UserRepositoryMySQL())
                .setUserMapper(new UserMapper().getMapper())
                .setUserName("user0")
                .setPeriod(new Date(2000, 1, 1), new Date(2010, 1, 1))
                .generate("/home/citadin/Desktop");

        assertTrue(true);
    }
}
