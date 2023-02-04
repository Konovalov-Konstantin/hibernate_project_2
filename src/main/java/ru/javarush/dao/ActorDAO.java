package ru.javarush.dao;

import org.hibernate.SessionFactory;
import ru.javarush.entity.Actor;

public class ActorDAO extends GenericDAO<Actor> {

    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
