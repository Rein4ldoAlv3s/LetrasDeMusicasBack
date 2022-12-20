package com.reinaldo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Artista {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O campo nome é requerido.")
	private String nome;

	@NotEmpty(message = "O campo Gênero Musical é requerido.")
	private String generoMusical;

	@NotEmpty(message = "O campo país de origem é requerido.")
	private String paisDeOrigem;

	@NotEmpty(message = "O artista deve possuir pelo menos um integrante.")
	private String integrantes;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagem_id", referencedColumnName = "id")
	private FileData imagem;
	
}
