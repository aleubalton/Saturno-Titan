package com.zenit.saturno.repository;

import com.zenit.saturno.domain.Modelo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Modelo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

  @Query("select modelo from Modelo modelo where modelo.nombre =:desc")
  Optional<Modelo> findByDesc(@Param("desc") String desc);
}
