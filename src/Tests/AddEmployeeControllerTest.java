package Tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AddEmployeeControllerTest {

//    @Rule    public final SessionFactoryRule sessionFactoryRule;
    private static EntityManager entityManager = null;
    private EntityManagerFactory entityManagerFactory = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
    }


    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    void setToEmployee() {
//        Employee driver = new Driver("Jan","Kowalski",43,"Mężczyzna","Kierowca", "JanKowalski@ms.com", 2200);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        System.out.println(entityManagerFactory);
        System.out.println(entityManager);
        assertEquals(2,1+1);

    }
}