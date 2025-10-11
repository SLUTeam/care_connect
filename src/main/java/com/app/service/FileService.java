package com.app.service;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.enumeration.ImageType;
import com.app.thirdparty.AWSS3Helper;
import com.app.util.file.AWSS3DirectoryUtil;

@Service
public class FileService {
	
	@Autowired
	AWSS3Helper s3UploadService;
	
	public String getDirectoryPath(ImageType imageType, UUID id) {
		String path = "";
		switch (imageType) {
				
			case CATEGORY:
				path = AWSS3DirectoryUtil.DIRECTORY_CATEGORY+"/"+id;
				break;

			case PRODUCT:
				path = AWSS3DirectoryUtil.DIRECTORY_PRODUCT+"/"+id;
				break;
	
			default:
				break;
		}
		return path;
	}
	
	public boolean uploadImage( ImageType imageType, UUID id,String fileName, String base64String) throws Exception {
		s3UploadService.uploadFileToS3Bucket(Base64.decodeBase64(base64String.substring(base64String.indexOf("64") + 3)), 
				getDirectoryPath(imageType, id), fileName);
		return true;
	}
	
	public byte[] downloadImage(ImageType imageType,UUID id,String fileName) throws Exception {
		byte[] data = s3UploadService.getFileFromS3Bucket(getDirectoryPath(imageType, id), fileName);
		return data;
	}
	 
	 
}
