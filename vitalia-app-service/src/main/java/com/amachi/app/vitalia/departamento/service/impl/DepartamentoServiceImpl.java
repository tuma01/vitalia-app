package com.amachi.app.vitalia.departamento.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;

import com.amachi.app.vitalia.departamento.dto.search.DepartamentoSearchDto;
import com.amachi.app.vitalia.departamento.entity.Departamento;
import com.amachi.app.vitalia.departamento.repository.DepartamentoRepository;
import com.amachi.app.vitalia.departamento.specification.DepartamentoSpecification;
import com.amachi.app.vitalia.country.entity.Country;
import com.amachi.app.vitalia.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@RequiredArgsConstructor
@Service
public class DepartamentoServiceImpl implements GenericService<Departamento, DepartamentoSearchDto> {

    private final DepartamentoRepository departamentoRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<Departamento> getAll() {
        return departamentoRepository.findAll();
    }


    @Override
    public Page<Departamento> getAll(DepartamentoSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Departamento> specification = new DepartamentoSpecification(searchDto);
        return departamentoRepository.findAll(specification, pageable);
    }

    @Transactional
    @Override
    public Optional<Departamento> getById(Long id) {
        checkNotNull(id);
        Optional<Departamento> departamentoOptional = departamentoRepository.findById(id);
        if (departamentoOptional.isEmpty()) {
//            throw new VitaliaException(ApiErrorEnum.OBJECT_NOT_FOUND, id);
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        Hibernate.initialize(departamentoOptional.get().getCountry());
        return departamentoOptional;
    }

    @Override
    public Departamento create(Departamento entity) {
        checkNotNull(entity);
        checkNotNull(entity.getCountry());
        Optional<Country> countryOptional = countryRepository.findById(entity.getCountry().getId());
        if (countryOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", entity.getCountry().getId());
        }
        return departamentoRepository.save(entity);
    }

    @Override
    public Departamento update(Long id, Departamento entity) {
        checkNotNull(id);
        checkNotNull(entity.getCountry());
        if (departamentoRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        Optional<Country> countryOptional = countryRepository.findById(entity.getCountry().getId());
        if (countryOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", entity.getCountry().getId());
        }
        entity.setCountry(countryOptional.get());
        entity.setId(id);
        return departamentoRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        checkNotNull(id);
        return departamentoRepository.findById(id)
                .map(departamento -> {
                    departamentoRepository.delete(departamento);
                    return true;
                })
                .orElse(false);
    }


//    @Override
//    public Departamento getDepartamento(Integer idDepartamento) {
//        checkNotNull(idDepartamento);
//        Optional<Departamento> departamentoOptional = departamentoRepository.findById(idDepartamento);
//        if (departamentoOptional.isEmpty()) {
//            throw new HospitalException(ApiErrorEnum.OBJECT_NOT_FOUND, idDepartamento);
//        }
//        return departamentoOptional.get();
//    }
//
//    @Override
//    public Departamento addDepartamento(Departamento departamento) {
//        checkNotNull(departamento);
//        return departamentoRepository.save(departamento);
//    }
//
//    @Override
//    public Departamento updateDepartamento(Integer idDepartamento, Departamento departamento) {
//        checkNotNull(idDepartamento);
//        if (departamentoRepository.findById(idDepartamento).isEmpty()) {
//            throw new HospitalException(ApiErrorEnum.OBJECT_NOT_FOUND, idDepartamento);
//        }
//        departamento.setIdDepartamento(idDepartamento);
//        return departamentoRepository.save(departamento);
//    }
//
//    @Override
//    public void deleteDepartamento(Integer idDepartamento) {
//        checkNotNull(idDepartamento);
//        Optional<Departamento> departamentoOptional = departamentoRepository.findById(idDepartamento);
//        if (departamentoOptional.isEmpty()) {
//            return;
//        }
//        departamentoRepository.delete(departamentoOptional.get());
//    }



//    @Override
//    public List<Departamento> findAllCountries() {
//        return departamentoRepository.findAll();
//    }
//
//    @Override
//    public Page<Departamento> getCountries(Integer pageIndex, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
//        return departamentoRepository.findAll(pageable);
//    }

//    @Override
//    public PageResponse<DepartamentoDto> getCountries(Integer page, Integer size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
//        Page<Departamento> countries = departamentoRepository.findAll(pageable);
//        List<DepartamentoDto> departamentoDtos = countries.map(departamento -> DepartamentoDto.builder()
//                .idDepartamento(departamento.getIdDepartamento())
//                .name(departamento.getName())
//                .iso(departamento.getIso())
//                .iso3(departamento.getIso3())
//                .niceName(departamento.getNiceName())
//                .numCode(departamento.getNumCode())
//                .phoneCode(departamento.getPhoneCode())
//                .build()).toList();
//        return new PageResponse<>(departamentoDtos,
//                countries.getNumber(),
//                countries.getSize(),
//                countries.getTotalElements(),
//                countries.getTotalPages(),
//                countries.isFirst(),
//                countries.isLast());
//    }

//    @Override
//    public PageResponse<DepartamentoDto> getCountries(Integer page, Integer size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
//        Page<Departamento> countries = departamentoRepository.findAll(pageable);
//        List<DepartamentoDto> departamentoDtos = countries.map(departamento -> DepartamentoDto.builder()
//                .idDepartamento(departamento.getIdDepartamento())
//                .name(departamento.getName())
//                .iso(departamento.getIso())
//                .iso3(departamento.getIso3())
//                .niceName(departamento.getNiceName())
//                .numCode(departamento.getNumCode())
//                .phoneCode(departamento.getPhoneCode())
//                .build()).toList();
//        return new PageResponse<>(departamentoDtos,
//                countries.getNumber(),
//                countries.getSize(),
//                countries.getTotalElements(),
//                countries.getTotalPages(),
//                countries.isFirst(),
//                countries.isLast());
//    }
}
