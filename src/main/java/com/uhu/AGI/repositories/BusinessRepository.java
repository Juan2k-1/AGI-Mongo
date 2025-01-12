package com.uhu.AGI.repositories;

import com.uhu.AGI.model.Business;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Repository
public interface BusinessRepository extends MongoRepository<Business, String>
{
    
    @Override
    public <S extends Business> List<S> findAll(Example<S> example, Sort sort);

    @Override
    public <S extends Business> List<S> findAll(Example<S> example);

    @Override
    public <S extends Business> List<S> insert(Iterable<S> entities);

    @Override
    public <S extends Business> S insert(S entity);

    @Override
    public List<Business> findAllById(Iterable<String> ids);

    @Override
    public List<Business> findAll();

    @Override
    public <S extends Business> List<S> saveAll(Iterable<S> entities);

    @Override
    public List<Business> findAll(Sort sort);

    @Override
    public void deleteAll();

    @Override
    public void deleteAll(Iterable<? extends Business> entities);

    @Override
    public void deleteAllById(Iterable<? extends String> ids);

    @Override
    public void delete(Business entity);

    @Override
    public void deleteById(String id);

    @Override
    public long count();

    @Override
    public boolean existsById(String id);

    @Override
    public Optional<Business> findById(String id);

    @Override
    public <S extends Business> S save(S entity);

    @Override
    public Page<Business> findAll(Pageable pageable);

    @Override
    public <S extends Business, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    @Override
    public <S extends Business> boolean exists(Example<S> example);

    @Override
    public <S extends Business> long count(Example<S> example);

    @Override
    public <S extends Business> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    public <S extends Business> Optional<S> findOne(Example<S> example);
    
    Page<Business> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
}
