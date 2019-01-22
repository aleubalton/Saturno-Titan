package com.zenit.saturno.repository;

import com.zenit.saturno.domain.Vehiculo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vehiculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

}
