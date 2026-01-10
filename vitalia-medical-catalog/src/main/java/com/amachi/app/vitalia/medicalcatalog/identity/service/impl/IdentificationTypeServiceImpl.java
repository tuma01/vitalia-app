package com.amachi.app.vitalia.medicalcatalog.identity.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import com.amachi.app.vitalia.medicalcatalog.identity.repository.IdentificationTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.identity.specification.IdentificationTypeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
@Transactional
public class IdentificationTypeServiceImpl implements GenericService<IdentificationType, IdentificationTypeSearchDto> {
    private final IdentificationTypeRepository repository;
    @Override public List<IdentificationType> getAll() { return repository.findAll(); }
    @Override public Page<IdentificationType> getAll(IdentificationTypeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        return repository.findAll(new IdentificationTypeSpecification(searchDto), PageRequest.of(pageIndex, pageSize, Sort.by("name")));
    }
    @Override public IdentificationType getById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("IdentificationType", "error", id)); }
    @Override public IdentificationType create(IdentificationType entity) { return repository.save(entity); }
    @Override public IdentificationType update(Long id, IdentificationType entity) { getById(id); entity.setId(id); return repository.save(entity); }
    @Override public void delete(Long id) { repository.delete(getById(id)); }
}
