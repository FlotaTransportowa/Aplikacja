package models;

import database.Employee;
import database.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class TrackModel {

    public ObservableList<Track> getAllTracks(){
        ObservableList<Track> tracks = FXCollections.observableArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        TypedQuery<Track> query = entityManager.createQuery("select t from Track t", Track.class);
        List<Track> trakcs1 = query.getResultList();
        entityManager.getTransaction().commit();

        tracks.addAll(trakcs1);

        entityManager.close();
        entityManagerFactory.close();
        return tracks;
    }
}
