package view.component;

import javax.swing.*;
import java.util.ArrayList;

public class ComponentsFetcher {

    private ArrayList<JComponent> components = new ArrayList<>();

    public void addComponent(JComponent component) {
        components.add(component);
    }

    public ArrayList<JComponent> getComponents() {
        return components;
    }
}
