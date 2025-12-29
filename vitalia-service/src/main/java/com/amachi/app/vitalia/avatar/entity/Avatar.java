package com.amachi.app.vitalia.avatar.entity;
import com.amachi.app.vitalia.common.entity.Auditable;
import com.amachi.app.vitalia.common.entity.Model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "AVATAR", indexes = {
        @Index(name = "IDX_AVATAR_USER", columnList = "USER_ID"),
        @Index(name = "IDX_AVATAR_TENANT", columnList = "TENANT_ID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avatar extends Auditable<String> implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
