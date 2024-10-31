package ma.projet.services;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
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
public class EmployeService implements IDao<Employe> {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean create(Employe o) {
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
    public Employe getById(int id) {
        Employe employe = null;
        try {
            Session session = sessionFactory.openSession();
            employe = session.get(Employe.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return employe;
    }

    @Override
    public List<Employe> getAll() {
        List<Employe> employes = null;
        try {
            Session session = sessionFactory.openSession();
            employes = session.createQuery("from Employe", Employe.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return employes;
    }
    public List<EmployeTache> getTachesRealisees(int employeId) {
        List<EmployeTache> employeTaches = null;
        try {
            Session session = sessionFactory.openSession();
            employeTaches = session.createQuery("from EmployeTache where employe.id = :employeId", EmployeTache.class)
                    .setParameter("employeId", employeId)
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return employeTaches;
    }

    public List<Projet> getProjetsGerés(int employeId) {
        List<Projet> projets = null;
        try {
            Session session = sessionFactory.openSession();
            projets = session.createQuery("select p from Projet p where p.chefDeProjet.id = :employeId", Projet.class)
                    .setParameter("employeId", employeId)
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return projets;
    }
}
