package com.reinaldo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.reinaldo.model.Musica;
import com.reinaldo.model.dto.MusicaDoArtista;


@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {

	@Query("SELECT obj FROM Musica obj WHERE obj.nome = ?1 AND obj.artista.id = ?2")
	Musica findByNomeAndArtistaId(String nome, Long artista_id);

	@Query("SELECT obj FROM Musica obj WHERE obj.artista.id = ?1")
	List<MusicaDoArtista> findAllMusicsOfAnArtist(Long artista_id);
	
	@Query("SELECT obj FROM Musica obj WHERE obj.nome = ?1")
	Musica findByNome(String nome);
	
}
