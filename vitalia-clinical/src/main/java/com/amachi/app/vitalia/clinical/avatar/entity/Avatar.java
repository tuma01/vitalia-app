package com.amachi.app.vitalia.clinical.avatar.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "MGT_AVATAR", indexes = {
        @Index(name = "IDX_AVATAR_USER", columnList = "USER_ID"),
        @Index(name = "IDX_AVATAR_TENANT", columnList = "TENANT_ID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Avatar extends Auditable<String> implements Model {

    // ID heredado de Auditable/BaseEntity

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "TENANT_CODE", nullable = false, length = 100)
    private String tenantCode;

    @Column(name = "FILENAME", length = 255)
    private String filename;

    @Column(name = "MIME_TYPE", length = 100)
    private String mimeType;

    @Lob
    @Column(name = "CONTENT", columnDefinition = "LONGBLOB")
    private byte[] content;

    @Column(name = "SIZE")
    private Long size;
}
