package com.amachi.app.core.geography.municipio.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import com.amachi.app.core.geography.municipio.dto.search.MunicipioSearchDto;
import com.amachi.app.core.geography.municipio.entity.Municipio;
import com.amachi.app.core.geography.municipio.repository.MunicipioRepository;
import com.amachi.app.core.geography.municipio.specification.MunicipioSpecification;
import com.amachi.app.core.geography.provincia.entity.Provincia;
import com.amachi.app.core.geography.provincia.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class MunicipioServiceImpl implements GenericService<Municipio, MunicipioSearchDto> {

    private final MunicipioRepository municipioRepository;
    private final ProvinciaRepository provinciaRepository;

    @Override
    public List<Municipio> getAll() {
        return municipioRepository.findAll();
    }

    @Override
    public Page<Municipio> getAll(MunicipioSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Municipio> specification = new MunicipioSpecification(searchDto);
        return municipioRepository.findAll(specification, pageable);
    }

    @Override
    public Municipio getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return municipioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Municipio.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Municipio create(Municipio entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);

        Long provinciaId = requireNonNull(entity.getProvincia(), ENTITY_MUST_NOT_BE_NULL).getId();
        requireNonNull(provinciaId, ID_MUST_NOT_BE_NULL);

        Provincia provincia = provinciaRepository.findById(provinciaId)
                .orElseThrow(() -> new ResourceNotFoundException(Provincia.class.getName(), "error.resource.not.found", entity.getProvincia().getId()));

        entity.setProvincia(provincia);
        return municipioRepository.save(entity);
    }

    @Override
    public Municipio update(Long id, Municipio entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        requireNonNull(entity.getProvincia(), "Provincia must not be null");
        requireNonNull(entity.getProvincia().getId(), "Provincia ID must not be null");

        // Verificar existencia de la provincia asociado
        Long provinciaId = requireNonNull(entity.getProvincia(), ENTITY_MUST_NOT_BE_NULL).getId();
        requireNonNull(provinciaId, ID_MUST_NOT_BE_NULL);

        // Verificar existencia del municipio
        municipioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Municipio.class.getName(), "error.resource.not.found", id));

        Provincia provincia = provinciaRepository.findById(provinciaId)
                .orElseThrow(() -> new ResourceNotFoundException(Provincia.class.getName(), "error.resource.not.found", entity.getProvincia().getId()));

        entity.setId(id);
        entity.setProvincia(provincia);
        return municipioRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Municipio municipio = municipioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Municipio.class.getName(), "error.resource.not.found", id));
        municipioRepository.delete(municipio);
    }
}

