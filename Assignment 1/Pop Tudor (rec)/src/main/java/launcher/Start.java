package launcher;


import database.Boostrap;

import java.sql.SQLException;

public class Start {

    public static boolean BOOTSTRAP = true;

    public static void main(String[] args) {
        bootstrap();

        ComponentFactory componentFactory = ComponentFactory.instance(false);
        componentFactory.getLoginView().setVisible();
    }

    private static void bootstrap() {
        if (BOOTSTRAP) {
            try {
                new Boostrap().execute();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }
}

