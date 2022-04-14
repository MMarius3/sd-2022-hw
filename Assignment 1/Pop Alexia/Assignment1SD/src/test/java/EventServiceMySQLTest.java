import Database.DBConnectionFactory;
import Repository.Event.EventRepository;
import Repository.Event.EventRepositoryMySQL;
import Service.Event.EventService;
import Service.Event.EventServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceMySQLTest {

    private static EventService eventService;
    private static EventRepository eventRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        eventRepository = new EventRepositoryMySQL(connection);
        eventService = new EventServiceMySQL(eventRepository);
        connection.isValid(10);
    }

    @Test
    public void add() throws  Exception{
        assertTrue(eventService.addEvent("test@yahoo.com","added client"));
    }

}
