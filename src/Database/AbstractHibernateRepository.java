package Database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateRepository<T extends Serializable> {

    private Class<T> myObject;

    SessionFactory sessionFactory = Database.SESSION;

    public final void setMyObject(Class<T> myObject) {
        this.myObject = myObject;
    }

    public T findOne(long id) {
        return (T) getCurrentSession().get(myObject, id);
    }

    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + myObject.getName()).list();
    }

    public void create(T entity) {
        getCurrentSession().persist(entity);
    }

    public void update(T entity) {
        getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}