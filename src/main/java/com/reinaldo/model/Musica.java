package com.reinaldo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Musica {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O nome não pode estar em branco!")
	private String nome;
	
	@NotEmpty(message = "O link do youtube não pode estar em branco!")
	private String linkYoutube;

	
	@Column(columnDefinition = "TEXT")
	@NotEmpty(message = "A letra da música não pode estar em branco!")
	private String letraDaMusica;

	
	@Column(columnDefinition = "TEXT")
	@NotEmpty(message = "A tradução não pode estar em branco!")
	private String traducao;

	
	@ManyToOne
	@JoinColumn(name = "artista_id")
	@NotNull(message = "O artista não pode estar em branco!")
	private Artista artista;

	public Musica() {
		super();
	}

	public Musica(Long id, String nome, String letraDaMusica, String traducao, Artista artista, String linkYoutube) {
		super();
		this.id = id;
		this.nome = nome;
		this.letraDaMusica = letraDaMusica;
		this.traducao = traducao;
		this.artista = artista;
		this.linkYoutube = linkYoutube;
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

	public String getLetraDaMusica() {
		return letraDaMusica;
	}

	public void setLetraDaMusica(String letraDaMusica) {
		this.letraDaMusica = letraDaMusica;
	}

	public String getTraducao() {
		return traducao;
	}

	public void setTraducao(String traducao) {
		this.traducao = traducao;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public String getLinkYoutube() {
		return linkYoutube;
	}

	public void setLinkYoutube(String linkYoutube) {
		this.linkYoutube = linkYoutube;
	}

	
	
	

}
