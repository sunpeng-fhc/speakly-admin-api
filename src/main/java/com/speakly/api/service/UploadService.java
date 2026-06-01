package com.speakly.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadAudio(MultipartFile file);
    String uploadImage(MultipartFile file);
}
