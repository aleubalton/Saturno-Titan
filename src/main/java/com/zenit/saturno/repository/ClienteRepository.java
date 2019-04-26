package com.zenit.saturno.repository;

import com.zenit.saturno.domain.Cliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

      @Query("select cliente from Cliente cliente where cliente.email =:email")
      Optional<Cliente> findByEmail(@Param("email") String email);

}
