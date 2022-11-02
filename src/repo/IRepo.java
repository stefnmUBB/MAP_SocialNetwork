package repo;

import domain.Entity;
import domain.validators.ValidationException;

public interface IRepo<ID, E extends Entity<ID>> {

    /**
     * Adds a new entity to the repo
     * @param entity entity to be added
     * @return added entity
     * @throws EntityAlreadyExistsException if id of entity already appears in the repo
     */
    E add(E entity) throws EntityAlreadyExistsException;

    /**
     * Updates entity based on its id
     * @param entity new entity with modified properties
     * @return changed entity
     */
    E update(E entity);

    /**
     * Remove entity by id
     * @param id Id of entity to delete
     * @return removed entity
     */
    E remove(ID id);

    /**
     * gets entity with given Id
     * @param id id to search for
     * @return entity having specified id, or null if no entity with that id was found
     */
    E getById(ID id);

    /**
     * @return list of all entities
     */
    Iterable<E> getAll();
}
