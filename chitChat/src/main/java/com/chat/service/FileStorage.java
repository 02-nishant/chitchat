package com.chat.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
	 String storeFile(MultipartFile file) throws IOException;
}
