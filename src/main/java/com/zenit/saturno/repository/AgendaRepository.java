package com.zenit.saturno.repository;

import com.zenit.saturno.domain.Agenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Agenda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query(value = "select distinct agenda from Agenda agenda left join fetch agenda.intervalos left join fetch agenda.diaNoLaborables",
        countQuery = "select count(distinct agenda) from Agenda agenda")
    Page<Agenda> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct agenda from Agenda agenda left join fetch agenda.intervalos left join fetch agenda.diaNoLaborables")
    List<Agenda> findAllWithEagerRelationships();

    @Query("select agenda from Agenda agenda left join fetch agenda.intervalos left join fetch agenda.diaNoLaborables where agenda.id =:id")
    Optional<Agenda> findOneWithEagerRelationships(@Param("id") Long id);

}
