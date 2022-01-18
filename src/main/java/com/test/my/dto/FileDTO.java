package com.test.my.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {

	private Long id;
	private String originalName;
	private String savedName;
	private String dirPath;
	private String fileUrl;
	private Long size;
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	
}
