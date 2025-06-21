package com.amachi.app.vitalia.municipio.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.departamento.entity.Departamento;
import com.amachi.app.vitalia.municipio.dto.search.MunicipioSearchDto;
import com.amachi.app.vitalia.municipio.entity.Municipio;
import com.amachi.app.vitalia.municipio.repository.MunicipioRepository;
import com.amachi.app.vitalia.municipio.specification.MunicipioSpecification;
import com.amachi.app.vitalia.provincia.entity.Provincia;
import com.amachi.app.vitalia.provincia.repository.ProvinciaRepository;
import com.amachi.app.vitalia.provincia.specification.ProvinciaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        requireNonNull(id, "ID must not be null");
        Optional<Municipio> municipioOptional = municipioRepository.findById(id);
        if (municipioOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return municipioOptional;
    }

    @Override
    public Municipio create(Municipio entity) {
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null
        requireNonNull(entity.getProvincia(), "Provincia must not be null");
        requireNonNull(entity.getProvincia().getId(), "Provincia ID must not be null");

        Provincia provincia = provinciaRepository.findById(entity.getProvincia().getId())
                .orElseThrow(() -> new ResourceNotFoundException("error.resource.not.found", entity.getProvincia().getId()));

        entity.setProvincia(provincia);
        return municipioRepository.save(entity);
    }

    @Override
    public Municipio update(Long id, Municipio entity) {
        requireNonNull(id, "ID must not be null");
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null
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
        requireNonNull(id, "ID must not be null");
        return municipioRepository.findById(id)
                .map(municipio -> {
                    municipioRepository.delete(municipio);
                    return true;
                })
                .orElse(false);
    }
}
//    @Override
//    public Municipio getMunicipio(Integer idMunicipio) {
//        checkNotNull(idMunicipio);
//        Optional<Municipio> municipioOptional = municipioRepository.findById(idMunicipio);
//        if (municipioOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, idMunicipio);
//        }
//        return municipioOptional.get();
//    }
//
//    @Override
//    public Municipio addMunicipio(Municipio municipio) {
//        checkNotNull(municipio);
//        checkNotNull(municipio.getProvincia());
//        Optional<Provincia> provinciaOptional = provinciaRepository.findById(municipio.getProvincia().getIdProvincia());
//        if (provinciaOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, municipio.getProvincia().getIdProvincia());
//        }
//        municipio.setProvincia(provinciaOptional.get());
//        return municipioRepository.save(municipio);
//    }
//
//    @Override
//    public Municipio updateMunicipio(Integer idMunicipio, Municipio municipio) {
//        checkNotNull(idMunicipio);
//        checkNotNull(municipio.getProvincia());
//
//        Optional<Municipio> municipioOptional = municipioRepository.findById(idMunicipio);
//        if (municipioOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, idMunicipio);
//        }
//        Optional<Provincia> provinciaOptional = provinciaRepository.findById(municipio.getProvincia().getIdProvincia());
//        if (provinciaOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, municipio.getProvincia().getIdProvincia());
//        }
//        municipio.setProvincia(provinciaOptional.get());
//        return municipioRepository.save(municipio);
//    }
//
//    @Override
//    public void deleteMunicipio(Integer idMunicipio) {
//        checkNotNull(idMunicipio);
//        Optional<Municipio> municipioOptional = municipioRepository.findById(idMunicipio);
//        if (municipioOptional.isEmpty()) {
//            return;
//        }
//        municipioRepository.delete(municipioOptional.get());
//    }
//
//    @Override
//    public Page<Municipio> getMunicipios(MunicipioSearchDto municipioSearchDto, Integer pageNumber, Integer pageSize, String sort) {
//        checkNotNull(municipioSearchDto);
//        var sortById = AppConstants.DEFAULT_SORT_BY + Municipio.class.getSimpleName();
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, AppUtils.getSort(sort, sortById));
//        return municipioRepository.getMunicipios(municipioSearchDto, pageable);
//    }
//
//    @Override
//    public List<Municipio> findAllMunicipios() {
//        return municipioRepository.findAll();
//    }

