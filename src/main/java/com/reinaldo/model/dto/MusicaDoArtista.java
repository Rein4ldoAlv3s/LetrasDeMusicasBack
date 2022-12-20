package com.reinaldo.model.dto;

import javax.validation.constraints.NotEmpty;

public interface MusicaDoArtista {
	
	Long getId();

	@NotEmpty(message = "O nome não pode estar em branco!")
	String getNome();

}
