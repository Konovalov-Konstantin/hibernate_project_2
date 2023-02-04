package ru.javarush.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.javarush.entity.Rental;

public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getUnreturnedRental() {
        Query<Rental> query = getCurrentSession().createQuery("select r from Rental r where r.returnDate is null ", Rental.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
