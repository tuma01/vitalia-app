package com.amachi.app.core.common.entity;

/**
 * Interface for soft deletion implementation.
 */
public interface SoftDeletable {
    Boolean getIsDeleted();
    void setIsDeleted(Boolean isDeleted);
    void delete();
}
