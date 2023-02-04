package ru.javarush.dao;

import org.hibernate.SessionFactory;
import ru.javarush.entity.Category;

public class CategoryDAO extends GenericDAO<Category> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
}
