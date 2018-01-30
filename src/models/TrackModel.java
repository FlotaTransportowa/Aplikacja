package models;

import database.Driver;
import database.Order;
import database.Machine;
import database.Track;
import fxModels.OrderFX;
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

    public ObservableList<Track> getAllNotAssigned() {
        ObservableList<Track> tracks = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        try{
            entityManager.getTransaction().begin();
            TypedQuery<Track> query = entityManager.createQuery("select e from Track e where e.driverOfTrack = null and e.machineOfTrack = null", Track.class);
            List<Track> tracks1 = query.getResultList();
            tracks.addAll(tracks1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return tracks;
    }

    public ObservableList<Track> getAllNotExecutedDriverTracks(Driver driver) {
        ObservableList<Track> tracks = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        try{
            entityManager.getTransaction().begin();
            TypedQuery<Track> query = entityManager.createQuery("select e from Track e where driverOfTrack = :driver and executed = 0", Track.class);
            query.setParameter("driver", driver);
            List<Track> tracks1 = query.getResultList();
            tracks.addAll(tracks1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return tracks;
    }


    public static void assignTrack(Machine machine, Track track, Driver driver){
        EntityManager entityManager = GlobalManager.getManager();
        try{
            entityManager.getTransaction().begin();
            track.setMachine(machine);
            track.setDriver(driver);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.refresh(track);
        }
    }

    public static void setExecuted(Track track){
        EntityManager entityManager = GlobalManager.getManager();
        try{
            entityManager.getTransaction().begin();
            track.setExecuted(true);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.refresh(track);
        }
    }

    public static void setAssignOrders(List<Order> orders){
        for(Order o : orders){
            o.getOrderState().assignOrder(o, GlobalManager.getManager());
        }
    }
}
