package ma.projet.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employe {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String telephone;

    @OneToOne(mappedBy = "chefDeProjet")
    private Projet projet;

    @OneToMany(mappedBy = "employe")
    private List<EmployeTache> employeTaches;

}
