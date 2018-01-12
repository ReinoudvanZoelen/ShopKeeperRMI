package Hibernate;

import Database.Database;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class GenericDatabaseTests {

    @Test
    public void testDummy() {
        Assert.assertTrue(true);
    }

    @Test
    public void test_ConnectionToDatabase() {
        boolean open = Database.SESSION.isOpen();

        Assert.assertTrue(open);
    }

}
