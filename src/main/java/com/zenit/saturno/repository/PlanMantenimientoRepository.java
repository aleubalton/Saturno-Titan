package com.zenit.saturno.repository;

import com.zenit.saturno.domain.PlanMantenimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlanMantenimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanMantenimientoRepository extends JpaRepository<PlanMantenimiento, Long> {

}
