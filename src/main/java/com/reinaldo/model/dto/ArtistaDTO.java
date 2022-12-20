package com.reinaldo.model.dto;

import javax.validation.constraints.NotEmpty;

import com.reinaldo.model.FileData;

public class ArtistaDTO {

	private Long id;

	@NotEmpty(message = "O campo nome é requerido.")
	private String nome;

	@NotEmpty(message = "O campo Gênero Musical é requerido.")
	private String generoMusical;

	@NotEmpty(message = "O campo país de origem é requerido.")
	private String paisDeOrigem;

	@NotEmpty(message = "O artista deve possuir pelo menos um integrante.")
	private String integrantes;

	private FileData imagem;

	public ArtistaDTO() {
		super();
	}

	public ArtistaDTO(Long id, String nome, String generoMusical, String paisDeOrigem, String integrantes,
			FileData imagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.generoMusical = generoMusical;
		this.paisDeOrigem = paisDeOrigem;
		this.integrantes = integrantes;
		this.imagem = imagem;
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

	public String getGeneroMusical() {
		return generoMusical;
	}

	public void setGeneroMusical(String generoMusical) {
		this.generoMusical = generoMusical;
	}

	public String getPaisDeOrigem() {
		return paisDeOrigem;
	}

	public void setPaisDeOrigem(String paisDeOrigem) {
		this.paisDeOrigem = paisDeOrigem;
	}

	public String getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(String integrantes) {
		this.integrantes = integrantes;
	}

	public FileData getImagem() {
		return imagem;
	}

	public void setImagem(FileData imagem) {
		this.imagem = imagem;
	}
	
	

}
