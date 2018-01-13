package Database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateRepository<T extends Serializable> {

    private Class<T> myObject;

    SessionFactory sessionFactory = Database.SESSION;

    public final void setMyObject(Class<T> myObject) {
        this.myObject = myObject;
    }

    public T findOne(long id) {
        Session session = sessionFactory.getCurrentSession();
        T object = (T) getCurrentSession().get(myObject, id);
        session.close();
        return object;

        // originally: return (T) getCurrentSession().get(myObject, id);
    }

    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        List<T> objects = session.createQuery("from " + myObject.getName()).list();
        session.close();
        return objects;

        // originally: return return getCurrentSession().createQuery("from " + myObject.getName()).list();
    }

    public void create(T entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
    }

    public void update(T entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
    }

    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
    }

    public void deleteById(long entityId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        T entity = findOne(entityId);
        delete(entity);
        transaction.commit();
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}