package com.zenit.saturno.repository;

import com.zenit.saturno.domain.DiaNoLaborable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiaNoLaborable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaNoLaborableRepository extends JpaRepository<DiaNoLaborable, Long> {

}
