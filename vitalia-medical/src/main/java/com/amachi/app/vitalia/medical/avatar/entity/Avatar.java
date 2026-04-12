package com.amachi.app.vitalia.medical.avatar.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

/**
 * Almacenamiento binario y metadata de avatares (SaaS Elite Tier).
 */
@Entity
@Table(name = "MED_AVATAR", indexes = {
    @Index(name = "IDX_AVATAR_USER", columnList = "USER_ID"),
    @Index(name = "IDX_AVATAR_TENANT", columnList = "TENANT_ID")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Gestión de avatares y recursos multimedia — SaaS Elite Tier")
public class Avatar extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "FILENAME", length = 255)
    private String filename;

    @Column(name = "MIME_TYPE", length = 100)
    private String mimeType;

    @Lob
    @Column(name = "CONTENT", columnDefinition = "LONGBLOB")
    private byte[] content;

    @Column(name = "CONTENT_SIZE")
    private Long size;

    @Override
    public void delete() {
        this.isDeleted = true;
    }
}
