package com.zenit.saturno.repository;

import com.zenit.saturno.domain.PrecioServicio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrecioServicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrecioServicioRepository extends JpaRepository<PrecioServicio, Long> {

}
