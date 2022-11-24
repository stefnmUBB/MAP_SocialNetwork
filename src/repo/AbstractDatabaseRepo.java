package repo;


import domain.Entity;
import domain.validators.IValidator;
import utils.Constants;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public abstract class AbstractDatabaseRepo<ID, E extends Entity<ID>> implements IRepo<ID, E>{
    private final String url;
    private final String username;
    private final String password;


    protected abstract PreparedStatement getSelectQuery(Connection conn) throws SQLException;
    protected abstract PreparedStatement getInsertQuery(Connection conn, E e) throws SQLException;
    protected abstract PreparedStatement getUpdateQuery(Connection conn, E e) throws SQLException;
    protected abstract PreparedStatement getRemoveQuery(Connection conn, ID id) throws SQLException;
    protected abstract E buildEntity(ResultSet result) throws SQLException;


    final IValidator<E> validator;
    public AbstractDatabaseRepo(IValidator<E> validator) {
        this("jdbc:postgresql://localhost:5432/SocialNetwork",
                "postgres","0000", validator);
    }

    public AbstractDatabaseRepo(String url, String username, String password, IValidator<E> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Iterable<E> getAll() {
        List<E> entities = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = getSelectQuery(connection);
            ResultSet result = ps.executeQuery()){
            while (result.next()){
                Long id = result.getLong("id");
                E entity = buildEntity(result);
                entity.setId((ID) id);
                entities.add(entity);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return entities;
    }

    @Override
    public E add(E entity) throws EntityAlreadyExistsException {
        validator.validate(entity);

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = getInsertQuery(connection, entity);
        ){
            ps.executeUpdate();
            try(ResultSet result = ps.getGeneratedKeys()){
                if(result.next()){
                    Long id = result.getLong(1);
                    entity.setId((ID)id);
                    return entity;
                } else {
                    return null;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    @Override
    public E update(E entity) {
        //String sql = getUpdateQuery(entity);
        //System.out.println(sql);

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = getUpdateQuery(connection, entity);
        ){
            ps.executeUpdate();
            try(ResultSet result = ps.getGeneratedKeys()){
                if(result.next()){
                    return entity;
                } else {
                    return null;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    @Override
    public E remove(ID id) {
        E entity = getById(id);
        if(entity==null) return null;
        //String sql = getRemoveQuery(id);

        //System.out.println(sql);

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = getRemoveQuery(connection, id);
        ){
            ps.executeUpdate();
            return entity;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    @Override
    public E getById(ID id) {
        //String sql = "Select * FROM public.\"" + getEntityTypeName()+"\" WHERE \"id\" = "+id;
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement ps = getSelectQuery(connection);
            ResultSet result = ps.executeQuery()){
            if (result.next()){
                E entity = buildEntity(result);
                entity.setId((ID) id);
                return entity;
            }
            return null;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }
}
