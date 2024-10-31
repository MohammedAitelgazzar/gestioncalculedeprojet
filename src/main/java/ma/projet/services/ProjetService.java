package ma.projet.services;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService implements IDao<Projet> {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean create(Projet o) {

        Transaction tx = null;
        boolean status = false;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            status = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Projet getById(int id) {
        Projet projet = null;
        try {
            Session session = sessionFactory.openSession();
            projet = session.get(Projet.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return projet;
    }

    @Override
    public List<Projet> getAll() {
        List<Projet> projets = null;
        try {
            Session session = sessionFactory.openSession();
            projets = session.createQuery("from Projet", Projet.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return projets;
    }
    public List<Tache> getTachesPlanifiées(int projetId) {
        List<Tache> taches = null;
        try {
            Session session = sessionFactory.openSession();
            taches = session.createQuery("from Tache where projet.id = :projetId", Tache.class)
                    .setParameter("projetId", projetId)
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return taches;
    }
    public void afficherTachesRealisees(int projetId) {
        try {
            Session session = sessionFactory.openSession();
            Projet projet = session.get(Projet.class, projetId);
            if (projet != null) {
                System.out.println("Projet : " + projet.getId());
                System.out.println("Nom : " + projet.getNom());
                System.out.println("Date début : " + projet.getDateDebut());

                System.out.println("Liste des tâches :");
                System.out.printf("%-3s %-20s %-20s %-20s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");

                for (Tache tache : projet.getTaches()) {
                    for (EmployeTache employeTache : tache.getEmployeTaches()) {
                        System.out.printf("%-3d %-20s %-20s %-20s%n", tache.getId(), tache.getNom(), employeTache.getDateDebutReelle(), employeTache.getDateFinReelle());
                    }
                }
            } else {
                System.out.println("Projet non trouvé.");
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
