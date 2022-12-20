package com.reinaldo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reinaldo.model.Artista;
import com.reinaldo.repository.ArtistaRepository;
import com.reinaldo.repository.FileDataRepository;
import com.reinaldo.services.exceptions.DataIntegrityViolationException;
import com.reinaldo.services.exceptions.ObjectNotFoundException;

@Service
public class ArtistaService {

	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private FileDataRepository fileDataRepository;
	

	public List<Artista> findAll() {
		return artistaRepository.findAll();
	}

	public Artista findById(Long artistaId) {
		Optional<Artista> obj = artistaRepository.findById(artistaId);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Artista com ID " + artistaId + " não encontrado!"));
	}

	public void delete(Long artistaId) {
		Artista artista = findById(artistaId);

		artistaRepository.deleteById(artistaId);
		
		if(artista.getImagem() != null) {
			storageService.deleteImageLocally(artista);
			storageService.deletePathOfBD(artista.getImagem().getId());
		}

		
	}

	public Artista save(Artista artista) {
		Artista obj = artistaRepository.findByNome(artista.getNome());
		if (obj != null && obj.getNome().equals(artista.getNome())) {
			
			//Deleta a imagem do banco de dados e armazenado localmente
			fileDataRepository.deleteById(artista.getImagem().getId());
			storageService.deleteImageLocally(artista); 
			
			throw new DataIntegrityViolationException(
					"Já existe um(a) " + "artista chamado " + artista.getNome() + ". Favor, informe outro nome ou outra imagem.");
		}
		return artistaRepository.save(artista);
	}

	public Artista update(Artista artista, Long artistaId) {
		Optional<Artista> artistaAtual = artistaRepository.findById(artistaId);

		// Verifica se existe o ID no banco
		findById(artistaId);

		if (findByNome(artista) != null && findByNome(artista).getId() != artistaId) {
			storageService.deleteImageLocally(artista);
			throw new DataIntegrityViolationException(
					"Já existe um " + artista.getNome() + " cadastrado. Informe outro nome!");
		}

		BeanUtils.copyProperties(artista, artistaAtual.get(), "id");
		Artista artistaSalvo = artistaRepository.save(artistaAtual.get());
		return artistaSalvo;
	}
	
	

	public Artista findByNome(Artista artista) {
		return artistaRepository.findByNome(artista.getNome());
	}

	public List<Artista> searchByNome(String nome) {
		return artistaRepository.searchByNome(nome);
	}

}
