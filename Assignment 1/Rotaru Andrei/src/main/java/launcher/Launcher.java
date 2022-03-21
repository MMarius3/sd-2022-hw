package launcher;

import controller.TableProcessing;
import database.Bootstrap;
import java.sql.SQLException;

public class Launcher {
    public static boolean BOOTSTRAP = false;

    public static void main(String[] args) {
        bootstrap();

        TableProcessing tableProcessing = TableProcessing.instance();
        ComponentFactory componentFactory = ComponentFactory.instance(false,tableProcessing);
        componentFactory.getLoginView().setVisible();
    }

    private static void bootstrap() {
        if (BOOTSTRAP) {
            try {
                new Bootstrap().execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
