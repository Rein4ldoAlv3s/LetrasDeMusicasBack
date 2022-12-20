package com.reinaldo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.reinaldo.model.Artista;
import com.reinaldo.model.Musica;
import com.reinaldo.model.dto.AllMusicasDTO;
import com.reinaldo.model.dto.MusicaDoArtista;
import com.reinaldo.services.MusicaService;

@RestController
@RequestMapping("/musicas")
@CrossOrigin("*")
public class MusicaResource {
	
	@Autowired
	private MusicaService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Musica> findById(@PathVariable Long id){
		Musica obj = service.findById(id);
		return ResponseEntity.ok().body(obj); 
	}
	
	
	
	@PostMapping
	public ResponseEntity<Musica> save(@Valid @RequestBody Musica musica){
		Musica obj = service.save(musica);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//Retorna todas as musicas do sistema juntamente com as informacoes do artista em cada musica
	@GetMapping
	public ResponseEntity<List<AllMusicasDTO>> findAll(){
		List<AllMusicasDTO> objDTO = service.findAll().stream().map(c -> new AllMusicasDTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	
	//Retorna todas as musicas do artista
	@GetMapping("/artistas")
	public ResponseEntity<List<MusicaDoArtista>> findAllMusicsOfAnArtist(@RequestParam("artistaId") Long artistaId){
		List<MusicaDoArtista> obj = service.findAllMusicsOfAnArtist(artistaId);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deletebyId(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Musica> update(@Valid @RequestBody Musica musica, @PathVariable("id") Long id){
		Musica obj = service.update(musica, id);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}

}
