package models;

import database.Driver;
import database.Permission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DriverModel{

    public static ObservableList<Driver> getAll() {
        ObservableList<Driver> drivers = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        try{
            entityManager.getTransaction().begin();
            TypedQuery<Driver> query = entityManager.createQuery("select e from Driver e", Driver.class);
            List<Driver> drivers1 = query.getResultList();
            drivers.addAll(drivers1);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            entityManager.getTransaction().commit();
        }

        return drivers;
    }

    public static void deleteDriverPermission(Driver driver, Permission p) {
        driver.getPermissions().remove(p);
        p.getDrivers().remove(driver);
    }

    public static void addDriverPermission(Driver driver, Permission permission)
    {
        List<Permission> driverPermission = driver.getPermissions();
        if(!driverPermission.contains(permission)){
            driverPermission.add(permission);

        }
        List<Driver> permissionDrivers = permission.getDrivers();
        if(!permissionDrivers.contains(driver)){
            permissionDrivers.add(driver);
        }
    }
}
