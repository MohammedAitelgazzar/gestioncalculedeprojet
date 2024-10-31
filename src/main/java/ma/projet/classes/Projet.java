package ma.projet.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    @OneToOne
    @JoinColumn(name = "chef_de_projet_id")
    private Employe chefDeProjet;

    @OneToMany(mappedBy = "projet")
    private List<Tache> taches;
}
