package manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
}
