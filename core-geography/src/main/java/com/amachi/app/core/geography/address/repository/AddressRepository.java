package com.amachi.app.core.geography.address.repository;

import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
}
