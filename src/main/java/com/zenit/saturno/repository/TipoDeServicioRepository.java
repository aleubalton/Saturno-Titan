package com.zenit.saturno.repository;

import com.zenit.saturno.domain.TipoDeServicio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoDeServicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDeServicioRepository extends JpaRepository<TipoDeServicio, Long> {

}
