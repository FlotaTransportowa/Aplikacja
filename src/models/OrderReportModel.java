package models;

import database.OrderReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderReportModel implements BaseModel<OrderReport>{
    /**
     * Szuka wszystkich Raportów zgłoszeń
     * @return Zwraca listę zgłoszeń
     */
    @Override
    public ObservableList<OrderReport> getAll() {
        ObservableList<OrderReport> reports = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        TypedQuery<OrderReport> query = entityManager.createQuery("select e from OrderReport e", OrderReport.class);
        List<OrderReport> reports1 = query.getResultList();
        entityManager.getTransaction().commit();

        reports.addAll(reports1);
        return reports;
    }
}
