package com.uvtdorms.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IPasswordResetTokenRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.request.ResetPasswordDto;
import com.uvtdorms.repository.entity.PasswordResetToken;
import com.uvtdorms.repository.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordResetService {
    private final IUserRepository userRepository;
    private final EmailService emailService;
    private final IPasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = PasswordResetToken.builder().user(user).token(token)
                .expiryDate(LocalDateTime.now().plusDays(3)).build();
        passwordResetTokenRepository.save(myToken);
    }

    public void forgotPassword(String email) {
        User user = userRepository.getByEmail(email)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByUserEmail(email);
        if (passwordResetToken.isPresent()) {
            throw new AppException("Token already sent", HttpStatus.BAD_REQUEST);
        }

        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        emailService.sendRestPasswordEmail(email, token);
    }

    public void validateToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException("Invalid token", HttpStatus.BAD_REQUEST));
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AppException("Token expired", HttpStatus.BAD_REQUEST);
        }
    }

    public void resetPassword(final ResetPasswordDto resetPasswordDto) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(resetPasswordDto.token())
                .orElseThrow(() -> new AppException("Invalid token", HttpStatus.BAD_REQUEST));
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AppException("Token expired", HttpStatus.BAD_REQUEST);
        }
        User user = passwordResetToken.getUser();
        String hashedPassword = passwordEncoder.encode(resetPasswordDto.newPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        passwordResetToken.setUsed(true);
        passwordResetTokenRepository.save(passwordResetToken);
    }
}
