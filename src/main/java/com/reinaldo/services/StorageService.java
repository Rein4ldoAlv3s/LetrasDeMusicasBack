package com.reinaldo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reinaldo.model.Artista;
import com.reinaldo.model.FileData;
import com.reinaldo.repository.FileDataRepository;
import com.reinaldo.services.exceptions.DataIntegrityViolationException;
import com.reinaldo.services.exceptions.ObjectNotFoundException;

@Service
public class StorageService {
	
	@Autowired
	private FileDataRepository fileDataRepository;
	
    private final String FOLDER_PATH="C:\\Users\\Reinaldo\\Desktop\\imagensRandom\\";
    
    //Salva a imagem localmente
	public FileData uploadImageToFileSystem(MultipartFile file) throws IOException {
		
		Optional<FileData> optFile = fileDataRepository.findByName(file.getOriginalFilename()); 
		if(optFile.isPresent()) {
			throw new DataIntegrityViolationException("Já existe uma imagem com esse nome, favor informe outro nome!");
		}
		
		
		String filePath = FOLDER_PATH + file.getOriginalFilename();
        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());
        
        file.transferTo(new File(filePath));

        if (fileData != null) {
            return fileData;
        }
        return null;
    }
	
	
	//Atualiza a imagem localmente
		public FileData updateImageToFileSystem(MultipartFile file, Artista model) throws IOException {
						
			String filePath = FOLDER_PATH + file.getOriginalFilename();
			
			Optional<FileData> imagemBD = fileDataRepository.findByName(file.getOriginalFilename());
			
			if(imagemBD.isPresent() && model.getImagem() != null && imagemBD.get().getId() != model.getImagem().getId()) {
				throw new DataIntegrityViolationException("Informe outra imagem para editar. Já existe uma com esse nome!");
			}
			if(imagemBD.isPresent() && model.getImagem() == null) {
				throw new DataIntegrityViolationException("Informe outra imagem para editar. Já existe uma com esse nome!");
			}
			
			Long imagemId = null;
			
			if(model.getImagem() != null) {
				imagemId = model.getImagem().getId();
				deleteImageLocally(model);
			}
						
			FileData fileData=fileDataRepository.save(FileData.builder()
					.id(imagemId)
	                .name(file.getOriginalFilename())
	                .type(file.getContentType())
	                .filePath(filePath).build());
			
	        file.transferTo(new File(filePath));        
	        
	        if (fileData != null) {
	            return fileData;
	        }
	        return null;
	    }
	
	
	
	//busca imagem do banco
	public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName); 
        
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
	
	public void deleteImageLocally(Artista artista) {
		String nomeImagem = artista.getImagem().getName();
		File imagem = new File(FOLDER_PATH + nomeImagem + "\\");
		imagem.delete();
	}
	
	public void deletePathOfBD(Long id) {
		fileDataRepository.deleteById(id);
	}

}
