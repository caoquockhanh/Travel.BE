package com.MyTravel.mytravel.security.services;

import com.MyTravel.mytravel.util.CodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {
	@Value("${STATIC_IMAGE_LOCATION}")
	private String STATIC_IMAGE_LOCATION;

	@PostConstruct
	public void init() throws IOException {
		// Create image folder if not exists
		Files.createDirectories(Paths.get(STATIC_IMAGE_LOCATION));
	}

	public void deleteImage(String fileName) throws IOException {
		Path imageFullPath = Paths.get(STATIC_IMAGE_LOCATION, fileName);
		Files.deleteIfExists(imageFullPath);
	}

	public String uploadImage(MultipartFile image) throws IOException {
		String extension = image.getOriginalFilename() == null
			? ""
			: CodeUtil.getFileNameExtension(image.getOriginalFilename());
		String fileName = buildImageFileName(extension);
		String filePath = Paths.get(STATIC_IMAGE_LOCATION, fileName).toString();
		try (var file = new BufferedOutputStream(new FileOutputStream(filePath))) {
			file.write(image.getBytes());
		}
		return fileName;
	}

	private String buildImageFileName(String extension) {
		return System.currentTimeMillis() + "-" + CodeUtil.generateRandomString() + extension;
	}
}
