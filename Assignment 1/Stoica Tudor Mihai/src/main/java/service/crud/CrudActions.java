package service.crud;

import org.modelmapper.ModelMapper;
import repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface CrudActions<T, TDTO> {

    CrudActions<T, TDTO> setRepository(AbstractRepository<TDTO> repository);

    CrudActions<T, TDTO> setMapper(ModelMapper mapper);

    Optional<T> getByID(long id);

    List<Optional<T>> getAll();

    void deleteByID(long id);

    void deleteAll();

    Optional<T> update(TDTO object);

    Optional<T> create(TDTO object);
}
