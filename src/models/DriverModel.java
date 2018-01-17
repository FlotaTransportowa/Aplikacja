package models;

import database.Driver;
import database.Employee;
import database.Permission;

import java.util.List;

public class DriverModel extends EmployeeModel {

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
