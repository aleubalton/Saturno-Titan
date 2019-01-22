package com.zenit.saturno.repository;

import com.zenit.saturno.domain.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Servicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    @Query(value = "select distinct servicio from Servicio servicio left join fetch servicio.tareas",
        countQuery = "select count(distinct servicio) from Servicio servicio")
    Page<Servicio> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct servicio from Servicio servicio left join fetch servicio.tareas")
    List<Servicio> findAllWithEagerRelationships();

    @Query("select servicio from Servicio servicio left join fetch servicio.tareas where servicio.id =:id")
    Optional<Servicio> findOneWithEagerRelationships(@Param("id") Long id);

}
