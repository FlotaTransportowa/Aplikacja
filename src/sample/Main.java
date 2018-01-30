package sample;

import database.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.GlobalManager;
import security.HashPassword;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainScreen.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("FSB - System zarzadzania");
        Scene scene = new Scene(root, 1280, 810);
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
        TypedQuery<Machine> query = entityManager.createQuery("select m from Maszyny m where m.id = :id", Machine.class);
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
        TypedQuery<Employee> query = entityManager.createQuery("select e from Pracownicy e where e.id = :id", Employee.class);
        query.setParameter("id", employeeId);
        employee = query.getSingleResult();
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

        return employee;
    }

    public static void create(List<Object> lista){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        for (Iterator<Object> i = lista.iterator(); i.hasNext();) {
            Object item = i.next();
            entityManager.persist(item);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }


    public static void createBasic() throws NoSuchAlgorithmException {
        EntityManager entityManager = GlobalManager.getManager();

        Principal employee = new Principal("Jan", "Nowak", 22, "Mężczyzna","Kierownik","a@wp.pl", 3000.0);
        Account account = new Account();
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
        Address address = new Address("Kielce", "23-093", "Sienkiewicza", "240");
        employee.setAddress(address);

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
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
        entityManager.getTransaction().commit();

        //dodaj zgloszenie

        NotifyTheft notification = new NotifyTheft();
        Date today = new Date();
        notification.setDate(today);
        notification.setDescription("Domyślny opis");
        notification.setStatus(Notification.NotifyStatus.SENT);
        notification.setEmployee(employee);
        notification.setMachine(machine);
        notification.setIfVictims(false);

        entityManager.getTransaction().begin();
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
        entityManager.persist(reportRepair);
        entityManager.getTransaction().commit();

        //ZADANIE NR 2 - WPROWADZIC DO BAZY RAPORT NADZORCZY

        Order order = new Order();
        OrderState state = new OrderNotConfirmed();
        order.setState(state);
        order.setType("budowa");
        order.setTimeLimitForCompletion(today);
        order.setComment("Default comment");
        order.setTitle("Zlecenie budowy domu");
        Address address2 = new Address("Warszawa", "20-103", "abc", "321c");
        order.setAddressOfOrder(address2);

        Order order2 = new Order();
        order2.setState(state);
        order2.setType("remont");
        order2.setTimeLimitForCompletion(today);
        order2.setComment("Komentarz");
        order2.setTitle("Zlecenie transportu materiału budowlanego");
        Address address3 = new Address("Poznań", "20-333", "abc", "11b");
        order2.setAddressOfOrder(address3);

        Track track = new Track();
        track.setDriver(employee);
        track.setName("Przykładowa nazwa");
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order2);
        track.setOrders(orders);
        track.setMachine(machine);
        track.setExecuted(false);

        Order order3 = new Order();
        order3.setState(state);
        order3.setType("budowa");
        order3.setTimeLimitForCompletion(today);
        order3.setComment("Default comment");
        order3.setTitle("Zlecenie budowy domu");
        order3.setAddressOfOrder(address2);

        Order order4 = new Order();
        order4.setState(state);
        order4.setType("remont");
        order4.setTimeLimitForCompletion(today);
        order4.setComment("Komentarz");
        order4.setTitle("Zlecenie transportu materiału budowlanego");
        order4.setAddressOfOrder(address3);

        entityManager.getTransaction().begin();

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.persist(track);
        entityManager.persist(order3);
        entityManager.persist(order4);
        entityManager.getTransaction().commit();
    }

    public static void useCreateMethod() throws NoSuchAlgorithmException{
        Driver employee = new Driver("Adam", "Kowalski", 25, "Mężczyzna","Kierowca","abc@wp.pl", 2200.0);
        Account account = new Account();
        employee.setAccount(account);
        //ustawiamy login i haslo dla konta
        account.setLogin("adam");
        account.setPassword(HashPassword.hashPassword("kowalski"));
        //tworzymy liste telefonow
        Phone phone1 = new Phone();
        phone1.setType("służbowy");
        phone1.setNumber("123-000-123");

        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        //ustawiamy liste telefonow dla pracownika
        employee.setPhones(phones);
        //tworzymy adres
        Address address = new Address("Staszów", "12-002", "-", "89a");
        employee.setAddress(address);

        GlobalManager.getManager().getTransaction().begin();
        GlobalManager.getManager().persist(employee);
        GlobalManager.getManager().getTransaction().commit();
    }

    public static void insertPermissions() throws NoSuchAlgorithmException{
        EntityManager entityManager = GlobalManager.getManager();

        Permission permission1 = new Permission();
        Permission permission2 = new Permission();
        Permission permission3 = new Permission();
        Permission permission4 = new Permission();
        Permission permission5 = new Permission();
        Permission permission6 = new Permission();
        Permission permission7 = new Permission();
        Permission permission8 = new Permission();
        Permission permission9 = new Permission();
        Permission permission10 = new Permission();


        permission1.setName("Koparki jednonaczyniowe do 25 ton masy całkowitej");
        permission1.setDescription("Uprawnienie do prowadzenia dużych koparek");

        permission2.setName("Koparkoładowarki");
        permission2.setDescription("Uprawnienie do operowania koparkoładowarkami");

        permission3.setName("Koparki wielonaczyniowe");
        permission3.setDescription("Uprawnienie do operowania koparkami wielonaczyniowymi");

        permission4.setName("Koparki wielonaczyniowe łańcuchowe do rowów");
        permission4.setDescription("Uprawnienie do operowania w rowach");

        permission5.setName("Spycharki - wszystkie typy");
        permission5.setDescription("Uprawnienie do jazdy wszystkimi rodzajami spychów");

        permission6.setName("Koparkospycharki");
        permission6.setDescription("Uprawnienie do jazdy koparkospycharkami");

        permission7.setName("Prawo jazdy kategorii B");
        permission7.setDescription("Prawo jazdy do prowadzenia pojazdów do 3.5 tony");

        permission8.setName("Prawo jazdy kategorii C");
        permission8.setDescription("Prawo jazdy do prowadzenia pojazdów powyżej 3.5 tony bez przyczepy");

        permission9.setName("Prawo jazdy kategorii C+E");
        permission9.setDescription("Prawo jazdy do prowadzenia pojazdów powyżej 3.5 tony z przyczepami");

        permission10.setName("Prawo jazdy kategorii T");
        permission10.setDescription("Prawo jazdy do prowadzenia pojazdów wolnobieżnych");

        entityManager.getTransaction().begin();
            GlobalManager.getManager().persist(permission1);
            GlobalManager.getManager().persist(permission2);
            GlobalManager.getManager().persist(permission3);
            GlobalManager.getManager().persist(permission4);
            GlobalManager.getManager().persist(permission5);
            GlobalManager.getManager().persist(permission6);
            GlobalManager.getManager().persist(permission7);
            GlobalManager.getManager().persist(permission8);
            GlobalManager.getManager().persist(permission9);
            GlobalManager.getManager().persist(permission10);
        entityManager.getTransaction().commit();
    }

    public static void insertOrderStates(){
        EntityManager entityManager = GlobalManager.getManager();
        OrderState orderNotConfirmed = new OrderNotConfirmed();
        orderNotConfirmed.setName("niezatwiedzone");
        OrderState orderNotAssigned = new OrderNotAssigned();
        orderNotAssigned.setName("nieprzydzielone");
        OrderState orderWaiting = new OrderWaiting();
        orderWaiting.setName("oczekujące");
        OrderState orderStillPerformed = new OrderStillPerformed();
        orderStillPerformed.setName("wykonywane");
        OrderState orderDone = new OrderDone();
        orderDone.setName("wykonane");
        OrderState orderCanceled = new OrderCanceled();
        orderCanceled.setName("anulowane");
        OrderState orderPaused = new OrderPaused();
        orderPaused.setName("wstrzymane");
        OrderState orderPosted = new OrderPosted();
        orderPosted.setName("zaksięgowane");


        entityManager.getTransaction().begin();
        entityManager.persist(orderNotConfirmed);
        entityManager.persist(orderNotAssigned);
        entityManager.persist(orderWaiting);
        entityManager.persist(orderStillPerformed);
        entityManager.persist(orderDone);
        entityManager.persist(orderCanceled);
        entityManager.persist(orderPaused);
        entityManager.persist(orderPosted);
        entityManager.getTransaction().commit();
    }

    public static void allInserts() throws NoSuchAlgorithmException {
        insertOrderStates();
        createBasic();
        useCreateMethod();
        insertPermissions();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        allInserts();
        launch(args); //Uruchomienie okienka aplikacji
        GlobalManager.closeManager(); //zamknięcie Singletonu EntityManagera
    }
}
