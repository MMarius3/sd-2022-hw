package repository.logger;

import helpers.validation.Notification;
import model.ActionLog;

import java.util.Date;
import java.util.List;

public interface LoggerRepository {
    Notification<ActionLog> addLog(Long userId, String message);

    Notification<List<ActionLog>> fetchLogs(Date from, Date to, Long forUserId);
}
