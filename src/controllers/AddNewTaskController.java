package controllers;

import database.Address;
import database.Order;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import manager.GlobalManager;
import models.AddressModel;
import validation.Pattern;
import validation.Validation;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class AddNewTaskController extends Controller
{
    private LoggedController loggedController;
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
        newTaskDate.getEditor().setDisable(true);
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
                            }
                        };
                    }
                };

        newTaskDate.setDayCellFactory(dayCellFactory);
        newTaskDate.setValue(LocalDate.now());
    }

    @FXML private void removeTab()
    {
        loggedController.removeTab(super.getThisTab());
    }

    @FXML void addTask() throws IOException
    {
        String type = newTaskType.getSelectionModel().getSelectedItem().toString();
        LocalDate date = newTaskDate.getValue();
        Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));

        if(!valid())
            return;

        Order order = setBasic((Date) Date.from(instant), type);

        Address address = setAddress();

        if(address == null)
            return;
        Address addrTmp = AddressModel.retExist(address);
        if(addrTmp != null){
            order.setAddressOfOrder(addrTmp);
        } else{
            order.setAddressOfOrder(address);
        }

        EntityManager entityManager = GlobalManager.getManager();
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
    }

    @FXML
    private Address setAddress(){
        Address address = new Address();
        address.setPostalCode(postalCode.getText());
        address.setLocality(locality.getText());
        address.setApartmentNumber(houseNumber.getText());
        address.setStreet(street.getText());
        return address;
    }

    @FXML
    private Order setBasic(Date deadline, String type){
        Order order = new Order();

        order.setTitle(newTaskTitle.getText());
        order.setComment(newTaskComment.getText());
        order.setTimeLimitForCompletion(deadline);
        if(type.equals(buildPattern))
            order.setType("budowa");
        else if(type.equals(renovationPattern))
            order.setType("remont");
        else if(type.equals(transportPattern))
            order.setType("transport");
        else
            order.setType("wewnętrzne");

        return order;
    }

    @FXML
    private boolean valid(){
        boolean validateFlag = true;
        if(!Validation.regexChecker(Pattern.postalCodePattern, postalCode.getText()) || postalCode.getText().isEmpty()){
            postalCode.setStyle(nonValidStyle);
            validateFlag = false;
        } else postalCode.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.stringPattern, locality.getText()) || locality.getText().isEmpty()){
            locality.setStyle(nonValidStyle);
            validateFlag = false;
        }else locality.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.stringPattern, street.getText()) || street.getText().isEmpty()){
            street.setStyle(nonValidStyle);
            validateFlag = false;
        } else street.setStyle(validStyle);
        if(houseNumber.getText().isEmpty()){
            houseNumber.setStyle(nonValidStyle);
            validateFlag = false;
        }else houseNumber.setStyle(validStyle);
        if(newTaskTitle.getText().isEmpty()){
            newTaskTitle.setStyle("-fx-background-color:#f9a7a7;");
            validateFlag = false;
        }else newTaskTitle.setStyle("-fx-background-color:#fff;");
        if(newTaskComment.getText().isEmpty()){
            newTaskComment.setStyle(nonValidStyle);
            validateFlag = false;
        }else newTaskComment.setStyle(validStyle);

        return validateFlag;
    }
}
