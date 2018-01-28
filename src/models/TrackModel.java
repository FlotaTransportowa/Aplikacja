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

        try{
            entityManager.getTransaction().begin();
            TypedQuery<Track> query = entityManager.createQuery("select e from Track e", Track.class);
            List<Track> tracks1 = query.getResultList();
            tracks.addAll(tracks1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return tracks;
    }
}
