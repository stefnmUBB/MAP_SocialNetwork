package service;

import domain.Entity;
import repo.EntityAlreadyExistsException;
import reports.AbstractReport;

import java.util.function.Predicate;

public interface IService<ID, E extends Entity<ID>> {
    AbstractReport add(E entity) throws EntityAlreadyExistsException;
    AbstractReport update(E entity);
    AbstractReport remove(ID id) throws EntityIdNotFoundException;

    boolean exists(Predicate<E> pred);

    public Iterable<E> getAll();

    ID generateNewId();
}
