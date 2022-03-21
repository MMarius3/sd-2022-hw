package Repository.Event;

import Model.Builder.EventBuilder;
import Model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import static Database.Constants.Tables.EVENT;

public class EventRepositoryMySQL implements EventRepository {


    private final Connection connection;

    public EventRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Event event) {
        try {
            PreparedStatement insertEventStatement = connection
                    .prepareStatement("INSERT INTO event values (null,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertEventStatement.setString(1, event.getUsername());
            insertEventStatement.setString(2, event.getAction());
            insertEventStatement.setDate(3,event.getDate());
            insertEventStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ObservableList<Event> findByUsername(String username) {
        ObservableList<Event> events = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from `" + EVENT + "` where `username`=\'" + username +"\'";
            ResultSet eventResultSet = statement.executeQuery(sql);

            while(eventResultSet.next()) {
                Event event = new EventBuilder()
                        .setId(eventResultSet.getLong("id"))
                        .setUsername(eventResultSet.getString("username"))
                        .setAction(eventResultSet.getString("action"))
                        .setDate(eventResultSet.getDate("date"))
                        .build();

                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
