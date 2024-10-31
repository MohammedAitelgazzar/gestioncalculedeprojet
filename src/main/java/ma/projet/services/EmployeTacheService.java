package ma.projet.services;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
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
public class EmployeTacheService implements IDao<EmployeTache> {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean create(EmployeTache employeTache) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employeTache);
            transaction.commit();
            return true;
        }
    }



    @Override
    public EmployeTache getById(int id) {
        EmployeTache employeTache = null;
        try {
            Session session = sessionFactory.openSession();
            employeTache = session.get(EmployeTache.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return employeTache;
    }

    @Override
    public List<EmployeTache> getAll() {
        List<EmployeTache> employeTaches = null;
        try {
            Session session = sessionFactory.openSession();
            employeTaches = session.createQuery("from EmployeTache ", EmployeTache.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return employeTaches;
    }
}
