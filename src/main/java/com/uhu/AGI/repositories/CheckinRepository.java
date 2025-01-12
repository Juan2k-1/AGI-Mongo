package com.uhu.AGI.repositories;

import com.uhu.AGI.model.Checkin;
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
import org.springframework.stereotype.Repository;

/**
 *
 * @author juald
 */
@Repository
public interface CheckinRepository extends MongoRepository<Checkin, String>
{

    @Override
    public <S extends Checkin> List<S> findAll(Example<S> example, Sort sort);

    @Override
    public <S extends Checkin> List<S> findAll(Example<S> example);

    @Override
    public <S extends Checkin> List<S> insert(Iterable<S> entities);

    @Override
    public <S extends Checkin> S insert(S entity);

    @Override
    public List<Checkin> findAllById(Iterable<String> ids);

    @Override
    public List<Checkin> findAll();

    @Override
    public <S extends Checkin> List<S> saveAll(Iterable<S> entities);

    @Override
    public List<Checkin> findAll(Sort sort);

    @Override
    public void deleteAll();

    @Override
    public void deleteAll(Iterable<? extends Checkin> entities);

    @Override
    public void deleteAllById(Iterable<? extends String> ids);

    @Override
    public void delete(Checkin entity);

    @Override
    public void deleteById(String id);

    @Override
    public long count();

    @Override
    public boolean existsById(String id);

    @Override
    public Optional<Checkin> findById(String id);

    @Override
    public <S extends Checkin> S save(S entity);

    @Override
    public Page<Checkin> findAll(Pageable pageable);

    @Override
    public <S extends Checkin, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    @Override
    public <S extends Checkin> boolean exists(Example<S> example);

    @Override
    public <S extends Checkin> long count(Example<S> example);

    @Override
    public <S extends Checkin> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    public <S extends Checkin> Optional<S> findOne(Example<S> example);
    
    @Query("{'business_id' : ?0}")
    Page<Checkin> findByBusinessId(String business, Pageable pageable);

}
