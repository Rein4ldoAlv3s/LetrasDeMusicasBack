package com.reinaldo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reinaldo.model.FileData;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long>{

	Optional<FileData> findByName(String name);

}
