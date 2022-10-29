package repo;

import domain.Entity;
import domain.validators.ValidationException;

public interface IRepo<ID, E extends Entity<ID>> {

    E add(E entity) throws EntityAlreadyExistsException;

    E update(E entity);

    E remove(ID id);

    E getById(ID id);

    Iterable<E> getAll();
}
