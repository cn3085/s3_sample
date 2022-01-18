package com.test.my.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class FileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String originalName;
	
	@Column(nullable = false)
	private String savedName;
	
	@Column(nullable = false)
	private String dirPath;
	
	@Column(nullable = false)
	private String fileUrl;
	
	@Column(nullable = false)
	private Long size;
	
	@CreatedDate
	private LocalDateTime regDate;
	
	@LastModifiedDate
	private LocalDateTime updDate;

	public void updateFile(String newFileOriginalFilename, String newFileSavedName, String newFileUrl, long newSize) {
		this.originalName = newFileOriginalFilename;
		this.savedName = newFileSavedName;
		this.fileUrl = newFileUrl;
		this.size = newSize;
	}
}
