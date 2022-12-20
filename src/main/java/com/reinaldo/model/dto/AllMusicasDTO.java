package com.reinaldo.model.dto;

import javax.validation.constraints.NotEmpty;

import com.reinaldo.model.Artista;
import com.reinaldo.model.Musica;

//Retorna todas as musicas do sistema juntamente com as informacoes do artista em cada musica
public class AllMusicasDTO {

	private Long id;

	@NotEmpty(message = "O nome não pode estar em branco!")
	private String nome;

	@NotEmpty(message = "O artista não pode estar em branco!")
	private Artista artista;
	
	public AllMusicasDTO() {
		super();
	}

	public AllMusicasDTO(Musica obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.artista = obj.getArtista();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}
	
	
}
