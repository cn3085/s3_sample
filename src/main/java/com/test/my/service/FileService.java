package com.test.my.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.test.my.dto.FileDTO;
import com.test.my.entity.FileEntity;
import com.test.my.repository.FileRepository;
import com.test.my.view.FileView;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileService {
	
	private final S3Service s3Service;
	private final FileRepository fileRepository;

	
	@Transactional(readOnly = true)
	public FileView saveFile(MultipartFile file) {
		
		String savedName = uploadFile(file);
		
		FileEntity fileEntity = FileEntity.builder()
										  .originalName(file.getOriginalFilename())
										  .savedName(savedName)
										  .fileUrl(s3Service.getFileUrl(savedName))
										  .dirPath("")
										  .size(file.getSize())
										  .build();
		
		fileEntity = fileRepository.save(fileEntity);
		
		return fileRepository.findById(fileEntity.getId(), FileView.class);
	}
	
	
	
	@Transactional(readOnly = true)
	public FileView saveFile(FileDTO fileDTO) {
		
		FileEntity fileEntity = FileEntity.builder()
										  .originalName(fileDTO.getOriginalName())
										  .savedName(fileDTO.getSavedName())
										  .fileUrl(fileDTO.getFileUrl())
										  .dirPath("")
										  .size(fileDTO.getSize())
										  .build();
		
		fileEntity = fileRepository.save(fileEntity);
		
		return fileRepository.findById(fileEntity.getId(), FileView.class);
	}
	
	
	public String uploadFile(MultipartFile file) {
		
		final String fileName = createFileName(file.getOriginalFilename());
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());
		
		try(InputStream is = file.getInputStream()){
			s3Service.uploadFile(is, objectMetadata, fileName);
		}catch(IOException e) {
			throw new IllegalStateException("파일 등록 중 오류가 발생했습니다.");
		}
		
		return fileName;
	}
	
	
	
	private String createFileName(String originalFilename) {
		return UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
	}
	
	
	
	private String getFileExtension(String originalFilename) {
		try {
			return originalFilename.substring(originalFilename.lastIndexOf('.'));
		}catch(StringIndexOutOfBoundsException e) {
			throw new IllegalStateException(String.format("잘못된 형식의 파일입니다. (%s)", originalFilename));
		}
	}


	
	public void deleteFile(Long fileId) {
		
		FileEntity fileEntity = fileRepository.findById(fileId).orElseThrow(() -> new IllegalStateException("Invalid File."));
		
		final String savedName = fileEntity.getSavedName();
		
		fileRepository.delete(fileEntity);
		
		s3Service.deleteFile(savedName);
		
	}

	
	
	@Transactional(readOnly = true)
	public List<FileView> getFiles() {
		return fileRepository.findAllByOrderByRegDateDesc(FileView.class);
	}


	
	@Transactional(rollbackFor = Exception.class)
	public Object putFile(Long fileId, MultipartFile newFile) {
		
		FileEntity fileEntity = fileRepository.findById(fileId).orElseThrow(() -> new IllegalStateException("Invalid File."));
		String oldFileSavedName = fileEntity.getSavedName();
		
		String newFileSavedName = uploadFile(newFile);
		fileEntity.updateFile(newFile.getOriginalFilename(), newFileSavedName, s3Service.getFileUrl(newFileSavedName), newFile.getSize());
		
		s3Service.deleteFile(oldFileSavedName);
		
		return fileRepository.findById(fileEntity.getId(), FileView.class);
	}

	
}
