package ma.projet.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Date dateDebutReelle;
    private Date dateFinReelle;
}
