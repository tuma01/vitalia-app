package com.amachi.app.vitalia.municipio.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.municipio.dto.search.MunicipioSearchDto;
import com.amachi.app.vitalia.municipio.entity.Municipio;
import com.amachi.app.vitalia.municipio.repository.MunicipioRepository;
import com.amachi.app.vitalia.municipio.specification.MunicipioSpecification;
import com.amachi.app.vitalia.provincia.entity.Provincia;
import com.amachi.app.vitalia.provincia.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.amachi.app.vitalia.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.vitalia.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
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
    public Optional<Municipio> getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Optional<Municipio> municipioOptional = municipioRepository.findById(id);
        if (municipioOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return municipioOptional;
    }

    @Override
    public Municipio create(Municipio entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        requireNonNull(entity.getProvincia(), "Provincia must not be null");
        requireNonNull(entity.getProvincia().getId(), "Provincia ID must not be null");

        Provincia provincia = provinciaRepository.findById(entity.getProvincia().getId())
                .orElseThrow(() -> new ResourceNotFoundException("error.resource.not.found", entity.getProvincia().getId()));

        entity.setProvincia(provincia);
        return municipioRepository.save(entity);
    }

    @Override
    public Municipio update(Long id, Municipio entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        requireNonNull(entity.getProvincia(), "Provincia must not be null");
        requireNonNull(entity.getProvincia().getId(), "Provincia ID must not be null");

        //    We use existsById as we only need to know if it's there, not fetch the full object yet.
        if (!municipioRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }

        Provincia provincia = provinciaRepository.findById(entity.getProvincia().getId())
                .orElseThrow(() -> new ResourceNotFoundException("error.resource.not.found", entity.getProvincia().getId()));

        entity.setId(id);
        entity.setProvincia(provincia);
        return municipioRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return municipioRepository.findById(id)
                .map(municipio -> {
                    municipioRepository.delete(municipio);
                    return true;
                })
                .orElse(false);
    }
}

