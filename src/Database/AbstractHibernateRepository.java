package Database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateRepository<T extends Serializable> {

    //http://www.baeldung.com/simplifying-the-data-access-layer-with-spring-and-java-generics

    SessionFactory sessionFactory = Database.SESSION;
    private Class<T> myObject;

    public final void setMyObject(Class<T> myObject) {
        this.myObject = myObject;
    }

    public T findOne(int id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        T object = (T) session.get(myObject, id);
        transaction.commit();
        session.close();
        return object;

        // originally: return (T) getCurrentSession().get(myObject, id);
    }

    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<T> objects = session.createQuery("from " + myObject.getName()).list();
        transaction.commit();
        session.close();
        return objects;

        // originally: return getCurrentSession().createQuery("from " + myObject.getName()).list();
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

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}