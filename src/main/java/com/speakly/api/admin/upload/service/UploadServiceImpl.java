package com.speakly.api.admin.upload.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl  implements UploadService{

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.public-url}")
    private String publicUrl;

    @Override
    public String uploadAudio(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            throw new RuntimeException("文件名不能为空");
        }

        String lowerName = originalFilename.toLowerCase();

        if (!lowerName.endsWith(".mp3")
                && !lowerName.endsWith(".wav")
                && !lowerName.endsWith(".m4a")) {
            throw new RuntimeException("只支持 mp3、wav、m4a 音频文件");
        }

        try {
            String objectName = "audio/" + System.currentTimeMillis() + "-" + originalFilename;

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return publicUrl + "/" + bucket + "/" + objectName;

        } catch (Exception e) {
            throw new RuntimeException("音频上传失败");
        }
    }


    @Override
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new RuntimeException("文件名不能为空");
        }

        String lowerName = originalFilename.toLowerCase();

        if (!lowerName.endsWith(".jpg")
                && !lowerName.endsWith(".jpeg")
                && !lowerName.endsWith(".png")
                && !lowerName.endsWith(".webp")) {
            throw new RuntimeException("只支持 jpg、jpeg、png、webp 图片");
        }

        try {
            String objectName = "images/" + System.currentTimeMillis() + "-" + originalFilename;

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return publicUrl + "/" + bucket + "/" + objectName;

        } catch (Exception e) {
            throw new RuntimeException("图片上传失败");
        }
    }
}
