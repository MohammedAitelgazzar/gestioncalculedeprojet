package ma.projet.services;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
        Session session = null;
        Transaction tx = null;
        boolean status = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            status = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return status;
    }

    @Override
    public Projet getById(int id) {
        Session session = null;
        Projet projet = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            projet = session.get(Projet.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return projet;
    }

    @Override
    public List<Projet> getAll() {
        Session session = null;
        List<Projet> projets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            projets = session.createQuery("from Projet", Projet.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return projets;
    }
    public List<Tache> getTachesPlanifiées(int projetId) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            taches = session.createQuery("from Tache where projet.id = :projetId", Tache.class)
                    .setParameter("projetId", projetId)
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }
    public void afficherTachesRealisees(int projetId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
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
        } finally {
            if (session != null) session.close();
        }
    }
}
