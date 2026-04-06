package com.amachi.app.core.geography.address.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.geography.address.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CommonRepository<Address, Long> {
}
