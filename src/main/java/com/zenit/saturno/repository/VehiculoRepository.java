package com.zenit.saturno.repository;

import com.zenit.saturno.domain.Vehiculo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Vehiculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    @Query("select vehiculo from Vehiculo vehiculo where vehiculo.patente =:patente")
    Optional<Vehiculo> findByPatente(@Param("patente") String patente);

}
