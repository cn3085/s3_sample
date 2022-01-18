package com.test.my.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.my.dto.FileDTO;
import com.test.my.service.FileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/js")
@RequiredArgsConstructor
public class FileJsController {
	
	private final FileService fileService;

	
	@PostMapping
	public ResponseEntity<Object> uploadFile(@RequestBody FileDTO fileDTO){
		return ResponseEntity.ok(fileService.saveFile(fileDTO));
	}

}
