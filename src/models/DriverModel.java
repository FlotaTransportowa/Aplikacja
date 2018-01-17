package models;

import database.Driver;
import database.Employee;
import database.Permission;

public class DriverModel extends EmployeeModel {

    public static void deleteDriverPermission(Driver driver, Permission p) {
        driver.getPermissions().remove(p);
        p.getDrivers().remove(driver);
    }
}
