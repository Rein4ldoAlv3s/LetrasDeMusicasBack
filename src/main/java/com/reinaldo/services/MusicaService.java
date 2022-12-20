package com.reinaldo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reinaldo.model.Musica;
import com.reinaldo.model.dto.MusicaDoArtista;
import com.reinaldo.repository.MusicaRepository;
import com.reinaldo.services.exceptions.DataIntegrityViolationException;
import com.reinaldo.services.exceptions.ObjectNotFoundException;

@Service
public class MusicaService {

	@Autowired
	private MusicaRepository musicaRepository;
	
	public List<Musica> findAll(){
		return musicaRepository.findAll();
	}
	
	public void deletebyId(Long id){
		findById(id);
		musicaRepository.deleteById(id);
	}
	
	public List<MusicaDoArtista> findAllMusicsOfAnArtist(Long artista_id) {
		return musicaRepository.findAllMusicsOfAnArtist(artista_id);
	}
	
	
	public Musica findById(Long musicaId) {
		Optional<Musica> obj = musicaRepository.findById(musicaId);
		return obj.orElseThrow(() -> new ObjectNotFoundException
				("Musica com ID " + musicaId + " não encontrado!")); 
	}
		
	public Musica save(Musica musica) {//carry on - angra
		
		Musica objMusica = musicaRepository.findByNomeAndArtistaId(musica.getNome(), musica.getArtista().getId()); //carry on - angra
		
		if(objMusica != null){
			throw new DataIntegrityViolationException(
					objMusica.getArtista().getNome() + " já possui uma musica chamada " + objMusica.getNome() + ". Favor, informe um nome diferente.");
		}
		return musicaRepository.save(musica);
	}
	
	public Musica update(Musica musica, Long musicaId) {
		Optional<Musica> musicaAtual = musicaRepository.findById(musicaId);
		
		//Verifica se existe o ID no banco
		findById(musicaId);
					
		if (findByNome(musica) != null && findByNome(musica).getId() != musicaId) {
			throw new DataIntegrityViolationException(
					"Já existe um cadastro de" + musica.getNome() + ". Favor, informe outro nome!");
		}

		BeanUtils.copyProperties(musica, musicaAtual.get(), "id");
		Musica musicaSalvo = musicaRepository.save(musicaAtual.get());
		return musicaSalvo;
	}
	
	public Musica findByNome(Musica musica) {
		return musicaRepository.findByNome(musica.getNome());
	}
}
