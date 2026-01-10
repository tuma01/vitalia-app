package com.amachi.app.core.geography.address.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.geography.address.dto.search.AddressSearchDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.address.repository.AddressRepository;
import com.amachi.app.core.geography.address.specification.AddressSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements GenericService<Address, AddressSearchDto> {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }


    @Override
    public Page<Address> getAll(AddressSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("direccion").descending());
        Specification<Address> specification = new AddressSpecification(searchDto);
        return addressRepository.findAll(specification, pageable);
    }

    @Transactional
    @Override
    public Address getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Address.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Address create(Address entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return addressRepository.save(entity);
    }

    @Override
    public Address update(Long id, Address entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);

        // Verificar existencia del addresses
        addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Address.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return addressRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Address addresses = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Address.class.getName(), "error.resource.not.found", id));
        addressRepository.delete(addresses);
    }
}
