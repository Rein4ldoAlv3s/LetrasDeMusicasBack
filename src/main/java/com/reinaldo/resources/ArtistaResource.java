package com.reinaldo.resources;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinaldo.model.Artista;
import com.reinaldo.model.FileData;
import com.reinaldo.model.dto.ArtistaDTO;
import com.reinaldo.repository.FileDataRepository;
import com.reinaldo.services.ArtistaService;
import com.reinaldo.services.StorageService;
import com.reinaldo.services.exceptions.DataIntegrityViolationException;

@RestController
@RequestMapping("/artistas")
@CrossOrigin("*")
public class ArtistaResource {

	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private FileDataRepository fileDataRepository;
	
	private Long idImagem;
	
	@GetMapping
	public ResponseEntity<List<Artista>> findAll(){
		List<Artista> list = artistaService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Artista> findById(@PathVariable Long id) throws IOException {
		Artista obj = artistaService.findById(id);
		
		
		return ResponseEntity.ok().body(obj); 
	}
	
	@GetMapping("/nomeArtista")
	public List<Artista> findByNome(@RequestParam("nome") String nome){
		List<Artista> obj = artistaService.searchByNome(nome);
		System.out.println(obj);
		return obj;  
	}
	
	//Salva a imagem localmente, atribui o id do objeto imagem no objeto artista e salva o artista
	@PostMapping
	public ResponseEntity<Artista> save(@Valid @RequestParam("artista") String artista, 
			@RequestParam(value = "file", required = false) MultipartFile file) throws IOException{
		
		System.out.println("******file teste " + file);
		
		FileData uploadImage = null;
		
		//Salva o objeto Imagem
		if(file != null) {
			uploadImage = storageService.uploadImageToFileSystem(file);
		}
		
		//Converte a String Artista em objeto Artista
		ObjectMapper mapper = new ObjectMapper();
		Artista model = mapper.readValue(artista, Artista.class);

		//Seta o id do objeto Imagem no objeto Artista
		model.setImagem(uploadImage);
		
		
		//Salva o artista
		Artista obj = artistaService.save(model);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		artistaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Artista> update(@Valid @RequestParam("artista") String artista, 
			@RequestParam(value = "file", required = false) MultipartFile file,
			@PathVariable("id") Long id) throws IOException{
						
		//Converte a String Artista em objeto Artista
		ObjectMapper mapper = new ObjectMapper();
		Artista model = mapper.readValue(artista, Artista.class);
				
		if(file != null) {
			FileData objImagem = storageService.updateImageToFileSystem(file, model);
			
			model.setImagem(objImagem);
			
			artistaService.update(model, id);
		}
		else {
			if(model.getImagem() != null) {
				
				storageService.deleteImageLocally(model);
				
				idImagem = model.getImagem().getId();
				
				model.setImagem(null);
				
				artistaService.update(model, id);
				
				storageService.deletePathOfBD(idImagem);
			} else {
				artistaService.update(model, id);
			}
		}
			
		return ResponseEntity.ok().build();
		
	}
	
}
