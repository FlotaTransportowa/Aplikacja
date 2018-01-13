package models;

import database.Dispatcher;
import database.Driver;
import database.Employee;
import database.Principal;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public interface BaseModel<T> {
    abstract ObservableList<T> getAll();
    abstract boolean valid(ArrayList<String> lista);
}
