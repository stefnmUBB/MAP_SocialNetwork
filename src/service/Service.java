package service;

import domain.Entity;
import factory.ReportFactory;
import repo.EntityAlreadyExistsException;
import repo.IRepo;
import reports.AbstractReport;
import reports.Report;
import reports.ReportType;

import java.util.Date;
import java.util.function.Predicate;

public abstract class Service<ID, E extends Entity<ID>> implements IService<ID, E> {
    IRepo<ID, E> repo;

    /**
     * Creates new service
     * @param repo repo to handle
     */
    public Service(IRepo<ID, E> repo) {
        this.repo = repo;
    }

    @Override
    public AbstractReport add(E entity) throws EntityAlreadyExistsException {
        entity.setId(generateNewId());
        return ReportFactory.getInstance().createReport(ReportType.ADD, repo.add(entity));
    }

    @Override
    public AbstractReport update(E entity) {
        return ReportFactory.getInstance().createReport(ReportType.UPDATE, repo.update(entity));
    }

    @Override
    public AbstractReport remove(ID id) throws EntityIdNotFoundException{
        var e = repo.remove(id);
        if(e==null) {
            throw new EntityIdNotFoundException();
        }
        return ReportFactory.getInstance().createReport(ReportType.REMOVE, e);
    }

    @Override
    public boolean exists(Predicate<E> pred)
    {
        for(E entity: repo.getAll()) {
            if(pred.test(entity))
                return true;
        }
        return false;
    }

    @Override
    public Iterable<E> getAll() {
        return repo.getAll();
    }
}
