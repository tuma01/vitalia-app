package com.amachi.app.vitalia.management.superadmin.repository;

import com.amachi.app.vitalia.management.superadmin.entity.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long>, JpaSpecificationExecutor<SuperAdmin> {

}
