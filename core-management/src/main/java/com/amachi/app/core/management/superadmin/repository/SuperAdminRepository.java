package com.amachi.app.core.management.superadmin.repository;

import com.amachi.app.core.management.superadmin.entity.SuperAdmin;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import com.amachi.app.core.auth.entity.User;
import java.util.Optional;

@Repository
public interface SuperAdminRepository extends CommonRepository<SuperAdmin, Long> {

    Optional<SuperAdmin> findByUser(User user);
}
