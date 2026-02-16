package com.mx.PruebaTecnicaChakray.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mx.PruebaTecnicaChakray.Entity.Usuarios;

public interface UsuariosDao extends JpaRepository<Usuarios, String>, JpaSpecificationExecutor<Usuarios>{
	
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Usuarios b WHERE b.taxId = :taxId")
    boolean existsByTaxId(String taxId);
    
    @Query("SELECT u FROM Usuarios u WHERE u.taxId = :taxId")
    Optional<Usuarios> findByTaxId(@Param("taxId") String taxId);

	
	
}
