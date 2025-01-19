package com.uhu.AGI.repositories;

import com.uhu.AGI.model.Tip;
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
public interface TipRepository extends MongoRepository<Tip, String>
{

    @Override
    public <S extends Tip> List<S> findAll(Example<S> example, Sort sort);

    @Override
    public <S extends Tip> List<S> findAll(Example<S> example);

    @Override
    public <S extends Tip> List<S> insert(Iterable<S> entities);

    @Override
    public <S extends Tip> S insert(S entity);

    @Override
    public List<Tip> findAllById(Iterable<String> ids);

    @Override
    public List<Tip> findAll();

    @Override
    public <S extends Tip> List<S> saveAll(Iterable<S> entities);

    @Override
    public List<Tip> findAll(Sort sort);

    @Override
    public void deleteAll();

    @Override
    public void deleteAll(Iterable<? extends Tip> entities);

    @Override
    public void deleteAllById(Iterable<? extends String> ids);

    @Override
    public void delete(Tip entity);

    @Override
    public void deleteById(String id);

    @Override
    public long count();

    @Override
    public boolean existsById(String id);

    @Override
    public Optional<Tip> findById(String id);

    @Override
    public <S extends Tip> S save(S entity);

    @Override
    public Page<Tip> findAll(Pageable pageable);

    @Override
    public <S extends Tip, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    @Override
    public <S extends Tip> boolean exists(Example<S> example);

    @Override
    public <S extends Tip> long count(Example<S> example);

    @Override
    public <S extends Tip> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    public <S extends Tip> Optional<S> findOne(Example<S> example);
    
    /**
     * Busca tips cuyo texto contiene una cadena ignorando mayúsculas y minúsculas.
     * 
     * @param text Cadena de texto a buscar.
     * @param pageable Paginación y orden.
     * @return Página de tips que coinciden con la búsqueda.
     */
    @Query("{'text' : ?0}")
    Page<Tip> findByTextContainingIgnoreCase(String text, Pageable pageable);
    
}
