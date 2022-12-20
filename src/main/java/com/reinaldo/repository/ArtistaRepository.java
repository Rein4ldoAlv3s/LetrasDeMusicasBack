package com.reinaldo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.reinaldo.model.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long>{

	@Query("SELECT obj FROM Artista obj WHERE obj.nome = ?1")
	Artista findByNome(String nome);
	
	@Query("SELECT obj FROM Artista obj WHERE obj.nome like CONCAT('%',:nome,'%')")
	List<Artista> searchByNome(@Param("nome") String nome);
	
	
}
