package ru.javarush.dao;

import org.hibernate.SessionFactory;
import ru.javarush.entity.Payment;

public class PaymentDAO extends GenericDAO<Payment> {
    public PaymentDAO(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
