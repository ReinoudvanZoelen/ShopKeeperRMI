package Database;

import _shared.Models.Product;
import org.hibernate.Session;

public class HibernateProductRepository {

    public boolean create(Product product) {
        Session session = Database.SESSION.openSession();

        session.beginTransaction();

        boolean result = session.save(product) != null;

        session.getTransaction().commit();

        session.close();

        return result;
    }
}
