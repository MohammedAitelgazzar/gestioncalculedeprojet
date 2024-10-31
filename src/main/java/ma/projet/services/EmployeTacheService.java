package ma.projet.services;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override
    public boolean create(EmployeTache employeTache) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employeTache);
            transaction.commit();
            return true;
        }
    }



    @Override
    public EmployeTache getById(int id) {
        Session session = null;
        EmployeTache employeTache = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employeTache = session.get(EmployeTache.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employeTache;
    }

    @Override
    public List<EmployeTache> getAll() {
        Session session = null;
        List<EmployeTache> employeTaches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employeTaches = session.createQuery("from EmployeTache ", EmployeTache.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employeTaches;
    }
}
