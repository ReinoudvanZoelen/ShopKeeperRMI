package Database;

import _shared.Models.Bestelling;

public class HibernateBestellingRepository extends AbstractHibernateRepository<Bestelling> {

    public HibernateBestellingRepository() {
        this.setMyObject(Bestelling.class);
    }
}
