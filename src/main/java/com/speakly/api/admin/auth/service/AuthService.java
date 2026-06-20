package com.speakly.api.admin.auth.service;


import com.speakly.api.admin.auth.dto.LoginRequest;
import com.speakly.api.admin.auth.dto.LoginResponse;
import com.speakly.api.domain.entity.AdminUser;
import com.speakly.api.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminUserRepository adminUserRepository;

    public LoginResponse login(LoginRequest request) {
        AdminUser user = adminUserRepository.findByUsername(request.getUserName())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // 先临时使用明文密码测试
        // 后面我们再改成 BCrypt + JWT
        if (!"123456".equals(request.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        String token = "mock-token-" + user.getUsername();
        String refreshToken = "mock-refresh-token-" + user.getUsername();

        return new LoginResponse(token, refreshToken);
    }
}
