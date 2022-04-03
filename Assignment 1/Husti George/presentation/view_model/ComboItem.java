package presentation.view_model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ComboItem {

    private int key;
    private String value;

    public ComboItem(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
