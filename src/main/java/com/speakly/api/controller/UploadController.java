package com.speakly.api.controller;

import com.speakly.api.common.ApiResponse;
import com.speakly.api.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/audio")
    public ApiResponse<Map<String, String>> uploadAudio(
            @RequestParam("file") MultipartFile file
    ) {
        String url = uploadService.uploadAudio(file);

        return ApiResponse.success(
                Map.of("url", url),
                "音频上传成功"
        );
    }

    @PostMapping("/image")
    public ApiResponse<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = uploadService.uploadImage(file);
        return ApiResponse.success(Map.of("url", url), "图片上传成功");
    }
}
