package models;

import database.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class TrackModel implements BaseModel<Track>{
    @Override
    public ObservableList<Track> getAll() {
        ObservableList<Track> tracks = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        TypedQuery<Track> query = entityManager.createQuery("select e from Track e", Track.class);
        List<Track> tracks1 = query.getResultList();
        entityManager.getTransaction().commit();

        tracks.addAll(tracks1);
        return tracks;
    }
}
