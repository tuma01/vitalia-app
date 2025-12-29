package com.amachi.app.vitalia.geography.address.repository;

import com.amachi.app.vitalia.geography.address.entity.Address;
import com.amachi.app.vitalia.geography.departamento.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
}
