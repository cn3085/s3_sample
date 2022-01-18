package com.test.my.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.test.my.config.S3Config;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3Service implements UploadService {
	
	private final AmazonS3 amazonS3;
	private final S3Config s3Config;

	@Override
	public void uploadFile(InputStream is, ObjectMetadata objectMetadata, String fileName) {
		amazonS3.putObject(new PutObjectRequest(s3Config.getBucket(), fileName, is, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
		
	}

	
	@Override
	public String getFileUrl(String fileName) {
		return amazonS3.getUrl(s3Config.getBucket(), fileName).toString();
	}


	public void deleteFile(String savedName) {
		if(amazonS3.doesObjectExist(s3Config.getBucket(), savedName)) {
			amazonS3.deleteObject(s3Config.getBucket(), savedName);
		}else {
			throw new IllegalStateException("존재하지 않는 파일");
		}
	}

}
