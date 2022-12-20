package com.reinaldo.resources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reinaldo.services.StorageService;

@RestController
@RequestMapping("/image")
@CrossOrigin("*")
public class StorageResource {

	@Autowired
	private StorageService service;
	
	@GetMapping("/fileSystem/")
	public ResponseEntity<Void> downloadImageFromFileSystem(){
		return ResponseEntity.ok().build();
	}
	
	
	// Busca a imagem do computador
	@GetMapping("/fileSystem/{fileName}")
	public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
		byte[] imageData = service.downloadImageFromFileSystem(fileName);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);

	}
}
