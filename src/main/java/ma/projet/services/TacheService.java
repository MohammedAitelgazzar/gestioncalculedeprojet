package ma.projet.services;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TacheService implements IDao<Tache> {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean create(Tache o) {
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
    public Tache getById(int id) {
        Tache tache = null;
        try {
            Session session = sessionFactory.openSession();
            tache = session.get(Tache.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return tache;
    }

    @Override
    public List<Tache> getAll() {

        List<Tache> taches = null;
        try {
            Session session = sessionFactory.openSession();
            taches = session.createQuery("from Tache", Tache.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return taches;
    }
    public List<Tache> getTachesPrixSup1000() {
        List<Tache> taches = null;
        try {
            Session session = sessionFactory.openSession();
            taches = session.createNamedQuery("Tache.findByPrixSup1000", Tache.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return taches;
    }
    public List<Tache> getTachesRealiseesEntreDeuxDates(Date dateDebut, Date dateFin) {
        List<Tache> taches = null;
        try {
            Session session = sessionFactory.openSession();
            Query<Tache> query = session.createQuery("SELECT t FROM Tache t JOIN t.employeTaches et WHERE et.dateDebutReelle BETWEEN :dateDebut AND :dateFin", Tache.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            taches = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return taches;
    }
}
