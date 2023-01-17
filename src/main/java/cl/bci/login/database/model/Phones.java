package cl.bci.login.database.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name="phones")
public class Phones {
    @Id
    private String number;
    private String citycode;
    private String contrycode;
    private String uuid;
}
