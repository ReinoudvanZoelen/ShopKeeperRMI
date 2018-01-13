package Database;

import _shared.Models.Product;

public class HibernateProductRepository extends AbstractHibernateRepository<Product> {

    public HibernateProductRepository() {
        this.setMyObject(Product.class);
    }
}
