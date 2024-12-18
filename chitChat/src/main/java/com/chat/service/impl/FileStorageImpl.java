package com.chat.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chat.service.FileStorage;

@Service
public class FileStorageImpl implements FileStorage {
	
	private final String uploadDir = "uploads/";

	@Override
	public String storeFile(MultipartFile file) throws IOException {
		 // Ensure the directory exists
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Create a unique file name to avoid conflicts
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Resolve the file path and store the file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the file path or URL for access (e.g., http://localhost/uploads/filename)
        return filePath.toString();  // For local storage
        // Alternatively, if hosting on a server, return URL path e.g., "http://localhost:8080/uploads/" + fileName
	}

}
