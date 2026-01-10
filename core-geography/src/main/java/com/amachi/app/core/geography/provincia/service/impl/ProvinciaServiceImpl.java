package com.amachi.app.core.geography.provincia.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import com.amachi.app.core.geography.departamento.repository.DepartamentoRepository;
import com.amachi.app.core.geography.provincia.dto.search.ProvinciaSearchDto;
import com.amachi.app.core.geography.provincia.entity.Provincia;
import com.amachi.app.core.geography.provincia.repository.ProvinciaRepository;
import com.amachi.app.core.geography.provincia.specification.ProvinciaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class ProvinciaServiceImpl implements GenericService<Provincia, ProvinciaSearchDto> {

    private final ProvinciaRepository provinciaRepository;
    private final DepartamentoRepository departamentoRepository;

    @Override
    public List<Provincia> getAll() {
        return provinciaRepository.findAll();
    }

    @Override
    public Page<Provincia> getAll(ProvinciaSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Provincia> specification = new ProvinciaSpecification(searchDto);
        return provinciaRepository.findAll(specification, pageable);
    }

    @Override
    public Provincia getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return provinciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Provincia.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Provincia create(Provincia entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        Long departamentoId = requireNonNull(entity.getDepartamento(), ENTITY_MUST_NOT_BE_NULL).getId();
        requireNonNull(departamentoId, ID_MUST_NOT_BE_NULL);

        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new ResourceNotFoundException(Departamento.class.getName(), "error.resource.not.found", departamentoId));

        entity.setDepartamento(departamento);
        return provinciaRepository.save(entity);
    }

    @Override
    public Provincia update(Long id, Provincia entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);

        // Verificar que el Departamento y su ID no sean nulos
        Long departamentoId = requireNonNull(entity.getDepartamento(), ENTITY_MUST_NOT_BE_NULL).getId();
        requireNonNull(departamentoId, ID_MUST_NOT_BE_NULL);

        // Verificar existencia de Provincia
        provinciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Provincia.class.getName(), "error.resource.not.found", id));

        // Verificar existencia de Departamento asociado
        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new ResourceNotFoundException(Departamento.class.getName(), "error.resource.not.found", departamentoId));

        entity.setId(id);
        entity.setDepartamento(departamento);
        return provinciaRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Provincia provincia = provinciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Provincia.class.getName(), "error.resource.not.found", id));
        provinciaRepository.delete(provincia);
    }
}