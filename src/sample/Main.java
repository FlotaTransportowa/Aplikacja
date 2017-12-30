package sample;

import database.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.dom4j.dom.DOMNodeHelper;
import security.HashPassword;

import javax.crypto.Mac;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainScreen.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("FSB - System zarzadzania");
        Scene scene = new Scene(root, 1280, 850);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/resources/general.css").toExternalForm());
    }

    public static Machine returnMachine(int machineId){
        Machine machine = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        TypedQuery<Machine> query = entityManager.createQuery("select m from Machine m where m.id = :id", Machine.class);
        query.setParameter("id", machineId);
        machine = query.getSingleResult();
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
        return machine;
    }

    public static Employee returnEmployee(int employeeId){
        Employee employee = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where e.id = :id", Employee.class);
        query.setParameter("id", employeeId);
        employee = query.getSingleResult();
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

        return employee;
    }

    public static void createBasic() throws NoSuchAlgorithmException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        Principal employee = new Principal();
        Account account = new Account();
        //ustawiamy pola pracownika
        employee.setFirstName("Hubert");
        employee.setLastName("Januszek");
        employee.setAge(22);
        employee.setEmail("aaa");
        employee.setStation("kierownik");
        employee.setSalary(3000.0);
        employee.setGender("M");
        employee.setAccount(account);
        //ustawiamy login i haslo dla konta
        account.setLogin("kierownik");
        account.setPassword(HashPassword.hashPassword("password"));
        //tworzymy liste telefonow
        Phone phone1 = new Phone();
        phone1.setType("domowy");
        phone1.setNumber("123-933-123");
        Phone phone2 = new Phone();
        phone2.setType("służbowy");
        phone2.setNumber("456-678-887");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        phones.add(phone2);
        //ustawiamy liste telefonow dla pracownika
        employee.setPhones(phones);
        //tworzymy adres
        Address address = new Address();
        address.setLocality("Kielce");
        address.setPostalCode("23-103");
        address.setStreet("Sienkiewicza");
        address.setApartmentNumber("23/2");
        employee.setAddress(address);

        Permission permission1 = new Permission();
        Permission permission2 = new Permission();

        permission1.setName("Uprawnienie 1");
        permission1.setDescription("Uprawnienie do prowadzenia...");

        permission2.setName("Uprawnienie 2");
        permission2.setDescription("Uprawnienie do operowania...");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission1);
        permissions.add(permission2);

        employee.setPermissions(permissions);


        entityManager.getTransaction().begin();
        //dodajemy kazdy!!! obiekt
        entityManager.persist(employee);
        entityManager.persist(account);
        entityManager.persist(phone1);
        entityManager.persist(phone2);
        entityManager.persist(address);
        entityManager.persist(permission1);
        entityManager.persist(permission2);
        entityManager.getTransaction().commit();

        //ZADANIE NR 1 - WPROWADZIC DO BAZY PRACOWNIKA TECHNICZNEGO

        Machine machine = new Machine();
        machine.setRegistrationNumber("TKI RAND");
        machine.setVIN("RAND");
        machine.setEfficient(true);
        machine.setBusy(false);
        MachineType machineType = new MachineType();
        machineType.setMark("Scania");
        machineType.setModel("CR16");
        machineType.setType("ciężarowy");
        machine.setType(machineType);

        entityManager.getTransaction().begin();
        //dodajemy kazdy!!! obiekt
        entityManager.persist(machine);
        entityManager.persist(machineType);
        entityManager.getTransaction().commit();

        //dodaj zgloszenie

        NotifyTheft notification = new NotifyTheft();
        Date today = new Date();
        notification.setDate(today);
        notification.setDescription("Domyślnie ukradły samochód");
        notification.setStatus("wysłane");
        notification.setEmployee(employee);
        notification.setMachine(machine);
        notification.setIfVictims(false);

        entityManager.getTransaction().begin();
        //dodajemy kazdy!!! obiekt
        entityManager.persist(notification);
        entityManager.getTransaction().commit();

        //dodaj raport
        ReportRepair reportRepair = new ReportRepair();
        reportRepair.setRepairCost(500.0);
        reportRepair.setDate(today);
        reportRepair.setTitle("Raport naprawy rozbitego auta");
        reportRepair.setDescription("Tam uważać następnym razem");

        RepairPart repairPart1 = new RepairPart();
        RepairPart repairPart2 = new RepairPart();
        repairPart1.setName("Lampa led");
        repairPart1.setSymbol("23IG3");
        repairPart1.setPrice(200.0);
        repairPart2.setName("Część");
        repairPart2.setSymbol("222G3");
        repairPart2.setPrice(500.0);
        List<RepairPart> repairParts = new ArrayList<>();
        repairParts.add(repairPart1);
        repairParts.add(repairPart2);
        reportRepair.setRepairParts(repairParts);
        reportRepair.setResult("naprawiony");

        entityManager.getTransaction().begin();
        //dodajemy kazdy!!! obiekt
        entityManager.persist(reportRepair);
        entityManager.persist(repairPart1);
        entityManager.persist(repairPart2);
        entityManager.getTransaction().commit();

        //ZADANIE NR 2 - WPROWADZIC DO BAZY RAPORT NADZORCZY

        Order order = new Order();
        order.setType("budowa");
        order.setTimeLimitForCompletion(today);
        order.setComment("Default comment");
        Address address2 = new Address();
        address2.setLocality("Warszawa");
        address2.setPostalCode("20-103");
        address2.setStreet("abc");
        address2.setApartmentNumber("321c");
        order.setAddress(address2);

        Order order2 = new Order();
        order2.setType("remont");
        order2.setTimeLimitForCompletion(today);
        order2.setComment("Komentarz");
        Address address3 = new Address();
        address3.setLocality("Poznań");
        address3.setPostalCode("20-333");
        address3.setStreet("abc");
        address3.setApartmentNumber("11b");
        order2.setAddress(address3);

        Track track = new Track();
        track.setDriver(employee);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order2);
        track.setOrders(orders);
        track.setMachine(machine);
        track.setAssigned(false);
        track.setExecuted(false);

        entityManager.getTransaction().begin();
        //dodajemy kazdy!!! obiekt
        entityManager.persist(track);
        entityManager.persist(order);
        entityManager.persist(address2);
        entityManager.persist(order2);
        entityManager.persist(address3);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        createBasic(); // tworzenie rekodrów
        launch(args); //Uruchomienie okienka aplikacji

    }
}
