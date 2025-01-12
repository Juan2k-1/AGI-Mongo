package com.uhu.AGI.repositories;

import com.uhu.AGI.model.Review;
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
public interface ReviewRepository extends MongoRepository<Review, String>
{

    @Override
    public <S extends Review> List<S> findAll(Example<S> example, Sort sort);

    @Override
    public <S extends Review> List<S> findAll(Example<S> example);

    @Override
    public <S extends Review> List<S> insert(Iterable<S> entities);

    @Override
    public <S extends Review> S insert(S entity);

    @Override
    public List<Review> findAllById(Iterable<String> ids);

    @Override
    public List<Review> findAll();

    @Override
    public <S extends Review> List<S> saveAll(Iterable<S> entities);

    @Override
    public List<Review> findAll(Sort sort);

    @Override
    public void deleteAll();

    @Override
    public void deleteAll(Iterable<? extends Review> entities);

    @Override
    public void deleteAllById(Iterable<? extends String> ids);

    @Override
    public void delete(Review entity);

    @Override
    public void deleteById(String id);

    @Override
    public long count();

    @Override
    public boolean existsById(String id);

    @Override
    public Optional<Review> findById(String id);

    @Override
    public <S extends Review> S save(S entity);

    @Override
    public Page<Review> findAll(Pageable pageable);

    @Override
    public <S extends Review, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    @Override
    public <S extends Review> boolean exists(Example<S> example);

    @Override
    public <S extends Review> long count(Example<S> example);

    @Override
    public <S extends Review> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    public <S extends Review> Optional<S> findOne(Example<S> example);
    
    @Query("{'user_id' : ?0}")
    Page<Review> findByUserIdContainingIgnoreCase(String userId, Pageable pageable);

}
