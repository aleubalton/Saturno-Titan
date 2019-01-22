package com.zenit.saturno.repository;

import com.zenit.saturno.domain.Intervalo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Intervalo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntervaloRepository extends JpaRepository<Intervalo, Long> {

}
