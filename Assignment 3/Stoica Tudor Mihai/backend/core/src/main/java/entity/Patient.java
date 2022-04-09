package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Patient {
    private int id;
    private String name;
    private String personalNumericalCode;
    private String birthDate;
    private String address;
}
