package repository.logger;

import helpers.validation.Notification;
import lombok.RequiredArgsConstructor;
import model.ActionLog;
import repository.user.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class LoggerRepositoryPostgres implements LoggerRepository {
    private final Connection connection;
    private final UserRepository userRepository;

    @Override
    public Notification<ActionLog> addLog(Long userId, String message) {
        Notification<ActionLog> newLogNotification = new Notification<>();
        if (userId == null) {
            userId = Long.valueOf(0);
        }
        try {
            PreparedStatement insertLog = connection
                    .prepareStatement("insert into log (user_id, action, creation_date) VALUES " +
                                    "(?, ?, date(now()));",
                            Statement.RETURN_GENERATED_KEYS);
            insertLog.setLong(1, userId);
            insertLog.setString(2, message);

            insertLog.executeUpdate();

            ResultSet rs = insertLog.getGeneratedKeys();
            rs.next();
            long logId = rs.getLong(1);
            ActionLog log =
                    ActionLog.builder().user(userRepository.findById(userId)).action(message)
                            .id(logId).build();
            newLogNotification.setResult(log);
            return newLogNotification;
        } catch (SQLException e) {
            newLogNotification.addError("Error with database");
            return newLogNotification;
        }
    }

    @Override
    public Notification<List<ActionLog>> fetchLogs(Date from, Date to, Long forUserId) {
        List<ActionLog> logs = new ArrayList<>();
        Notification<List<ActionLog>> notification = new Notification<>();
        try {
            PreparedStatement fetchAccountsSql =
                    connection.prepareStatement("select * from log where " +
                            "creation_date > " +
                            "date(?) and " +
                            "creation_date < date(?) and user_id = ?;");
            fetchAccountsSql.setString(1, from.toString());
            fetchAccountsSql.setString(2, to.toString());
            fetchAccountsSql.setLong(3, forUserId);

            ResultSet logsResultSet = fetchAccountsSql.executeQuery();
            while (logsResultSet.next()) {
                ActionLog actionLog =
                        ActionLog.builder().id(logsResultSet.getLong("id"))
                                .action(logsResultSet.getString("action"))
                                .user(userRepository.findById(logsResultSet.getLong("user_id")))
                                .build();
                logs.add(actionLog);
            }
            notification.setResult(logs);
            return notification;
        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Error with database");
        }
        return notification;
    }
}
