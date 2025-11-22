package com.amachi.app.vitalia.avatar.controller;

import com.amachi.app.vitalia.avatar.dto.AvatarDto;
import com.amachi.app.vitalia.avatar.entity.Avatar;
import com.amachi.app.vitalia.avatar.mapper.AvatarMapper;
import com.amachi.app.vitalia.avatar.service.AvatarService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;
    private final AvatarMapper mapper;


    // Para el usuario autenticado
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvatarDto> uploadOwnAvatar(
//            @AuthenticationPrincipal(expression = "id") Long userId,
            // TODO desactivar esto y activar arriba
            @RequestParam("userId") Long userId,
            @RequestParam("tenantCode") String tenantCode,
            @RequestParam("file") MultipartFile file) {
        Avatar entity = avatarService.saveAvatar(file, userId, tenantCode);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @GetMapping
    public ResponseEntity<byte[]> getAvatar(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @RequestParam("tenantCode") String tenantCode) {

        Avatar avatar = avatarService.getAvatarEntity(userId, tenantCode);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, avatar.getMimeType())
                .body(avatar.getContent());
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal(expression = "id") Long userId,
                                       @RequestParam(required = false) String tenantCode) {
        avatarService.deleteAvatar(userId, tenantCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/avatar-user")
    public ResponseEntity<byte[]> getByUser(@RequestParam @NotNull Long userId,
                                            @RequestParam(required = false) String tenantCode) {
        byte[] data = avatarService.getAvatar(userId, tenantCode);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(data);
    }

    @PutMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvatarDto> updateAvatar(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @RequestParam("tenantCode") String tenantCode,
            @RequestParam("file") MultipartFile file) {

        Avatar updated = avatarService.updateAvatar(userId, tenantCode, file);
        return ResponseEntity.ok(mapper.toDto(updated));
    }
}
