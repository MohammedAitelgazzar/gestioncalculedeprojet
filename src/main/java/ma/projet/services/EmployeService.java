package ma.projet.services;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
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
    public Employe getById(int id) {
        Session session = null;
        Employe employe = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employe = session.get(Employe.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employe;
    }

    @Override
    public List<Employe> getAll() {
        Session session = null;
        List<Employe> employes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employes = session.createQuery("from Employe", Employe.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employes;
    }
    public List<EmployeTache> getTachesRealisees(int employeId) {
        Session session = null;
        List<EmployeTache> employeTaches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employeTaches = session.createQuery("from EmployeTache where employe.id = :employeId", EmployeTache.class)
                    .setParameter("employeId", employeId)
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employeTaches;
    }

    public List<Projet> getProjetsGerés(int employeId) {
        Session session = null;
        List<Projet> projets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            projets = session.createQuery("select p from Projet p where p.chefDeProjet.id = :employeId", Projet.class)
                    .setParameter("employeId", employeId)
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return projets;
    }
}
