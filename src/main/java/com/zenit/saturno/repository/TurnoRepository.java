package com.zenit.saturno.repository;

import com.zenit.saturno.domain.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Turno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    @Query(value = "select distinct turno from Turno turno left join fetch turno.servicios",
        countQuery = "select count(distinct turno) from Turno turno")
    Page<Turno> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct turno from Turno turno left join fetch turno.servicios")
    List<Turno> findAllWithEagerRelationships();

    @Query("select turno from Turno turno left join fetch turno.servicios where turno.id =:id")
    Optional<Turno> findOneWithEagerRelationships(@Param("id") Long id);

}
