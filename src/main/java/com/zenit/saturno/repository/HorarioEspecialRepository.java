package com.zenit.saturno.repository;

import com.zenit.saturno.domain.HorarioEspecial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HorarioEspecial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioEspecialRepository extends JpaRepository<HorarioEspecial, Long> {

}
