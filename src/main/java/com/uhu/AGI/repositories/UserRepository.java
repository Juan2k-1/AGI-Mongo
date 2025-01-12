package com.uhu.AGI.repositories;

import com.uhu.AGI.model.User;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.FluentQuery;

/**
 *
 * @author juald
 */
public interface UserRepository extends MongoRepository<User, String>
{

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort);

    @Override
    public <S extends User> List<S> findAll(Example<S> example);

    @Override
    public <S extends User> List<S> insert(Iterable<S> entities);

    @Override
    public <S extends User> S insert(S entity);

    @Override
    public List<User> findAllById(Iterable<String> ids);

    @Override
    public List<User> findAll();

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities);

    @Override
    public List<User> findAll(Sort sort);

    @Override
    public void deleteAll();

    @Override
    public void deleteAll(Iterable<? extends User> entities);

    @Override
    public void deleteAllById(Iterable<? extends String> ids);

    @Override
    public void delete(User entity);

    @Override
    public void deleteById(String id);

    @Override
    public long count();

    @Override
    public boolean existsById(String id);

    @Override
    public Optional<User> findById(String id);

    @Override
    public <S extends User> S save(S entity);

    @Override
    public Page<User> findAll(Pageable pageable);

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    @Override
    public <S extends User> boolean exists(Example<S> example);

    @Override
    public <S extends User> long count(Example<S> example);

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example);

    @Query("{'name' : ?0}")
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
