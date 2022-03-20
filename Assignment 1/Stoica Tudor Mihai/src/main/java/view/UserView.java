package view;

import view.component.ComponentsFetcher;

import javax.swing.*;

import static javax.swing.text.View.Y_AXIS;

public class UserView extends JFrame {

    public UserView() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public UserView setComponents(ComponentsFetcher componentsFetcher) {
        componentsFetcher.getComponents().forEach(this::add);
        return this;
    }
}
