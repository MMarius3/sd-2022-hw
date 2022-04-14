package repository_layer.repository.action;

import bussiness_layer.models.Action;

import java.sql.Date;
import java.util.List;

public interface ActionRepository {

  boolean insert (Action action, String username);

  List<Action> retrieveAllByDate (Date startDate, Date endDate, String username);
}
