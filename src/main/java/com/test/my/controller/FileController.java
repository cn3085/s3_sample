package com.test.my.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.my.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {
	
	private final FileService fileService;
	
	
	@GetMapping
	public ResponseEntity<Object> getFileList(){
		return ResponseEntity.ok(fileService.getFiles()); 
	} 

	
	@PostMapping
	public ResponseEntity<Object> uploadFile(MultipartFile file){
		return ResponseEntity.ok(fileService.saveFile(file));
	}
	
	
	@DeleteMapping
	public ResponseEntity<Object> deleteFile(Long fileId){
		fileService.deleteFile(fileId);
		return ResponseEntity.ok("삭제 완료");
	}
	
	
	@PutMapping
	public ResponseEntity<Object> putFile(@RequestParam(required = true)Long fileId, @RequestParam(required = true)MultipartFile file){
		return ResponseEntity.ok(fileService.putFile(fileId, file));
	}
	
}
