package dev.sriharsha.bookstore.backend.service;

import dev.sriharsha.bookstore.backend.exception.FileHandleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    @Value("${application.services.file.FILE_UPLOAD_PATH}")
    private String fileUploadPath;

    public String save(
            @NonNull Integer userId,
            @NonNull MultipartFile file
    ) {
        String uploadPath = fileUploadPath + separator + "users" + separator + userId;
        File targetFolder = new File(uploadPath);
        if (!targetFolder.exists()) {
            boolean createdFolders = targetFolder.mkdirs();
            if (!createdFolders) {
                log.error("Failed to create the target folder for image upload");
                throw new FileHandleException("Failed to upload file");
            }
        }

        String fileExtension = extractFileExtension(file);
        String filePath = uploadPath + separator + currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(filePath);
        try {
            Files.write(targetPath, file.getBytes());
            return filePath;
        } catch (IOException e) {
            log.error("Failed to upload file to target path: {}", e.getMessage());
            throw new FileHandleException("Failed to upload file");
        }
    }

    public byte[] readFileFromPath(String filePath) {
        if (filePath.isEmpty() || filePath.isBlank()) {
            return null;
        }
        try {
            Path path = new File(filePath).toPath();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("failed to read file from location: {}", filePath);
        }
        return null;
    }

    private String extractFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new FileHandleException("Failed to upload file! Invalid file name");
        }
        int lastIndex = originalFilename.lastIndexOf(".");
        if (lastIndex == -1) {
            throw new FileHandleException("Failed to upload file! Invalid file extension");
        }
        String fileExtension = originalFilename.substring(lastIndex + 1);
        return fileExtension.toLowerCase();
    }
}
