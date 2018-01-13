package Database;

import _shared.Models.Klant;

public class HibernateKlantRepository extends AbstractHibernateRepository<Klant> {

    public HibernateKlantRepository() {
        this.setMyObject(Klant.class);
    }
}
