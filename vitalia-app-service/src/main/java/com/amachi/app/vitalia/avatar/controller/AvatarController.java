package com.amachi.app.vitalia.avatar.controller;

import com.amachi.app.vitalia.avatar.service.AvatarService;
import com.amachi.app.vitalia.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping
    public ResponseEntity<Void> uploadAvatar(@RequestParam MultipartFile file,
                                             @AuthenticationPrincipal User user) {
        avatarService.updateAvatar(user.getId(), file);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<byte[]> getOwnAvatar(@AuthenticationPrincipal User user) {
        byte[] avatar = avatarService.getAvatar(user.getId());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(avatar);
    }

    @GetMapping("/email")
    public ResponseEntity<byte[]> getAvatarByEmail(@RequestParam String email) {
        byte[] avatar = avatarService.getAvatarByEmail(email);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(avatar);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAvatar(@AuthenticationPrincipal User user) {
        avatarService.deleteAvatar(user.getId());
        return ResponseEntity.noContent().build();
    }
}
