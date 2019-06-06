package com.lesson5;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Item saveItem(Item item) {
        entityManager.persist(item);
        return item;
    }

    public Item updateItem(Item item) {
        entityManager.merge(item);
        return item;
    }

    public Item deleteItem(Long id) {
        Item item = entityManager.find(Item.class, id);
        entityManager.remove(item);
        return item;
    }

    public Item findById(long id) {
        return entityManager.find(Item.class, id);
    }
}
