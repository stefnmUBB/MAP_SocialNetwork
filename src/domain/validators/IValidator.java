package domain.validators;

public interface IValidator<T> {
    void validate(T entity) throws ValidationException;
}
