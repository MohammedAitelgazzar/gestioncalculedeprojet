package ma.projet;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.services.EmployeService;
import ma.projet.services.EmployeTacheService; // Ajoutez cette importation
import ma.projet.services.ProjetService;
import ma.projet.services.TacheService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeService employeService = new EmployeService();
        EmployeTacheService employeTacheService = new EmployeTacheService(); // Instance du service EmployeTache

        Projet projet1 = new Projet();
        projet1.setNom("Gestion de stock");
        projet1.setDateDebut(new Date());

        Projet projet2 = new Projet();
        projet2.setNom("Développement d'application");
        projet2.setDateDebut(new Date());

        projetService.create(projet1);
        projetService.create(projet2);

        Tache tache1 = new Tache();
        tache1.setNom("Analyse");
        tache1.setDateDebut(new Date());
        tache1.setDateFin(new Date());
        tache1.setPrix(800);
        tache1.setProjet(projet1);

        Tache tache2 = new Tache();
        tache2.setNom("Conception");
        tache2.setDateDebut(new Date());
        tache2.setDateFin(new Date());
        tache2.setPrix(1200);
        tache2.setProjet(projet1);

        Tache tache3 = new Tache();
        tache3.setNom("Développement");
        tache3.setDateDebut(new Date());
        tache3.setDateFin(new Date());
        tache3.setPrix(1500);
        tache3.setProjet(projet1);

        Tache tache4 = new Tache();
        tache4.setNom("Tests");
        tache4.setDateDebut(new Date());
        tache4.setDateFin(new Date());
        tache4.setPrix(500);
        tache4.setProjet(projet2);

        Tache tache5 = new Tache();
        tache5.setNom("Déploiement");
        tache5.setDateDebut(new Date());
        tache5.setDateFin(new Date());
        tache5.setPrix(1100);
        tache5.setProjet(projet2);

        tacheService.create(tache1);
        tacheService.create(tache2);
        tacheService.create(tache3);
        tacheService.create(tache4);
        tacheService.create(tache5);

        Employe employe1 = new Employe();
        employe1.setNom("Dupont");
        employe1.setPrenom("Jean");
        employe1.setTelephone("0123456789");

        Employe employe2 = new Employe();
        employe2.setNom("Martin");
        employe2.setPrenom("Claire");
        employe2.setTelephone("0987654321");

        employeService.create(employe1);
        employeService.create(employe2);

        // Création de l'EmployeTache
        EmployeTache employeTache = new EmployeTache();
        employeTache.setEmploye(employe1);
        employeTache.setTache(tache1);
        employeTache.setDateDebutReelle(new Date()); // Définir la date de début réelle
        employeTache.setDateFinReelle(new Date()); // Définir la date de fin réelle

        // Utilisation du service pour créer l'EmployeTache
        if (employeTacheService.create(employeTache)) {
            System.out.printf("EmployeTache créé : Employé %s %s pour la tâche %s.%n",
                    employe1.getNom(), employe1.getPrenom(), tache1.getNom());
        } else {
            System.out.println("Échec de la création de l'EmployeTache.");
        }

        projetService.afficherTachesRealisees(projet1.getId());

        List<Tache> tachesChères = tacheService.getTachesPrixSup1000();
        System.out.println("Tâches dont le prix est supérieur à 1000 DH :");
        for (Tache tache : tachesChères) {
            System.out.printf("Num: %d, Nom: %s, Prix: %.2f DH%n", tache.getId(), tache.getNom(), tache.getPrix());
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebut = sdf.parse("2024-10-31");
            Date dateFin = sdf.parse("2024-11-22");
            List<Tache> tachesEntreDates = tacheService.getTachesRealiseesEntreDeuxDates(dateDebut, dateFin);
            System.out.println("Tâches réalisées entre le 31/10/2024 et le 22/11/2024 :");
            for (Tache tache : tachesEntreDates) {
                System.out.printf("Num: %d, Nom: %s%n", tache.getId(), tache.getNom());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
