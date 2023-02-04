package ru.javarush.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public abstract class GenericDAO<T> {
    private final Class<T> clazz;
    private SessionFactory sessionFactory;

    protected GenericDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public T getById(int id) {
        return (T)getCurrentSession().get(clazz, id);
    }

    public List<T> getItems(int offset, int count){
        Query<T> query = getCurrentSession().createQuery("from " + clazz.getName(), clazz);
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<T> findAll(){
        return getCurrentSession().createQuery("from" + clazz.getName(), clazz).list();
    }

    public T save(T entity){
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public T update(T entity){
        return (T)getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(int id) {
        T entity = getById(id);
        delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


}
