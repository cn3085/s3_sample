package com.test.my.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.my.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long>{

	<T> T findById(Long fileId, Class<T> viewType);
	<T> List<T> findAllByOrderByRegDateDesc(Class<T> viewType);
}
