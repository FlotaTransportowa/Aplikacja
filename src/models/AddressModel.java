package models;

import database.Address;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Model adresu
 */
public class AddressModel implements BaseModel<Address>{

    /**
     * @return Zwraca wszystkie adresy
     */
    @Override
    public ObservableList<Address> getAll() {
        return null;
    }

    public static Address retExist(Address address){
        Address existAddress = null;
        EntityManager entityManager = GlobalManager.getManager();

        try {
            entityManager.getTransaction().begin();
            TypedQuery<Address> query = entityManager.createQuery("select a from Address a where postalCode = :code and locality = :local and street = :street and apartmentNumber = :houseNum ", Address.class);
            query.setParameter("code", address.getPostalCode());
            query.setParameter("local", address.getLocality());
            query.setParameter("street", address.getStreet());
            query.setParameter("houseNum", address.getApartmentNumber());
            query.setMaxResults(1);
            existAddress = query.getSingleResult();
        } catch (Exception e){
            System.out.println("Adres nie istnieje i został utworzony.");
        } finally {
            entityManager.getTransaction().commit();
            return existAddress;
        }

    }
}
