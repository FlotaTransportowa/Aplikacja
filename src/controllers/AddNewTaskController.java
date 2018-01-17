package controllers;

import database.Address;
import database.Order;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import manager.GlobalManager;
import models.AddressModel;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class AddNewTaskController extends LoggedController
{
    private String buildPattern = "Zlecenie Budowy",
        renovationPattern = "Zlecenie Remontu",
        transportPattern = "Zlecenie Transportu";
    private final String pattern = "yyyy-MM-dd";
    private DatePicker checkIn;
    @FXML private DatePicker newTaskDate;
    @FXML private TextField newTaskTitle;
    @FXML private ChoiceBox newTaskType;
    @FXML private ChoiceBox newTaskLocation;
    @FXML private TextArea newTaskComment;
    @FXML private Text newTaskStatus;
    @FXML private TextField postalCode, locality, street, houseNumber;

    @FXML void initialize() throws Exception
    {
        checkIn = new DatePicker();
        newTaskType.setItems(FXCollections.observableArrayList("Zlecenie Budowy", "Zlecenie Remontu", "Zlecenie Transportu", "Zlecenie Wewnętrzne"));
        newTaskType.getSelectionModel().selectFirst();
        newTaskDate.setValue(LocalDate.now());
        checkIn.setValue(LocalDate.now().minusDays(1));
        newTaskDate.setShowWeekNumbers(true);
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        newTaskDate.setConverter(converter);
        newTaskDate.setPromptText(pattern.toLowerCase());
        newTaskDate.requestFocus();

        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(
                                        checkIn.getValue().plusDays(1))
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                long p = ChronoUnit.DAYS.between(
                                        checkIn.getValue(), item
                                );
                                setTooltip(new Tooltip(
                                        "You're about to stay for " + p + " days")
                                );
                            }
                        };
                    }
                };

        newTaskDate.setDayCellFactory(dayCellFactory);
        newTaskDate.setValue(LocalDate.now());
    }

    @FXML void cancelTask() throws IOException
    {
        cancelSelectedView();
    }

    @FXML void addTask() throws IOException
    {
        Address address = null;
        String type = newTaskType.getSelectionModel().getSelectedItem().toString();
        LocalDate date = newTaskDate.getValue();
        Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
        Date deadline = Date.from(instant);
        Order order = new Order();
        order.setTitle(newTaskTitle.getText());
        order.setComment(newTaskComment.getText());
        order.setTitle(newTaskTitle.getText());
        order.setTimeLimitForCompletion(deadline);
        if(type.equals(buildPattern))
            order.setType("budowa");
        else if(type.equals(renovationPattern))
            order.setType("remont");
        else if(type.equals(transportPattern))
            order.setType("transport");
        else
            order.setType("wewnętrzne");
        address = setAndValidAddress(postalCode.getText(), locality.getText(), street.getText(), houseNumber.getText());
        if(address != null) {
            Address addrTmp = AddressModel.retExist(address);
            if(addrTmp != null){
                System.out.println("Adres istnieje");
                order.setAddressOfOrder(addrTmp);
            } else{
                System.out.println("Adres jeszcze nie istenieje");
                order.setAddressOfOrder(address);
            }

            EntityManager entityManager = GlobalManager.getManager();
            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();
        }
        else
            System.out.println("Adres jest nieprawidłowy!");

    }

    @FXML
    private Address setAndValidAddress(String postalCode, String locality, String street, String houseNum){
        Address address = new Address();
        if(!AddressModel.valid(postalCode, locality, street, houseNum)) return null;
        address.setPostalCode(postalCode);
        address.setLocality(locality);
        address.setApartmentNumber(houseNum);
        address.setStreet(street);
        return address;
    }
}
