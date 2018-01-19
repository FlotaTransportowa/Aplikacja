package manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class GlobalManager {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
    private static EntityManager entityManager = null;

    private GlobalManager(){}
    public static synchronized EntityManager getManager(){
        if(entityManager == null){
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    public static void closeManager(){
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void remove(Object o)
    {
        if(o!=null) {
            try {
                entityManager.remove(o);
            }
            catch (Exception ex)
            {
                System.err.println(ex);
            }
        }
    }
}
