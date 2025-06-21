package com.amachi.app.vitalia.provincia.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.departamento.entity.Departamento;
import com.amachi.app.vitalia.departamento.repository.DepartamentoRepository;
import com.amachi.app.vitalia.provincia.dto.search.ProvinciaSearchDto;
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

import static com.google.common.base.Preconditions.checkNotNull;
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
    public Optional<Provincia> getById(Long id) {
//        checkNotNull(id);
        requireNonNull(id, "ID must not be null");
        Optional<Provincia> provinciaOptional = provinciaRepository.findById(id);
        if (provinciaOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return provinciaOptional;
    }

    @Override
    public Provincia create(Provincia entity) {
        // Use requireNonNull for clearer intent and standard Java utility
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null
        requireNonNull(entity.getDepartamento(), "Departamento must not be null");
        requireNonNull(entity.getDepartamento().getId(), "Departamento ID must not be null");

        Departamento departamento = departamentoRepository.findById(entity.getDepartamento().getId())
                .orElseThrow(() -> new ResourceNotFoundException("error.resource.not.found", entity.getDepartamento().getId()));

        entity.setDepartamento(departamento);
        return provinciaRepository.save(entity);
    }

    @Override
    public Provincia update(Long id, Provincia entity) {

        // Use requireNonNull for clearer intent and standard Java utility
        requireNonNull(id, "ID must not be null");
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null
        requireNonNull(entity.getDepartamento(), "Departamento must not be null");
        requireNonNull(entity.getDepartamento().getId(), "Departamento ID must not be null");

        //    We use existsById as we only need to know if it's there, not fetch the full object yet.
        if (!provinciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }

        //    It's crucial to associate the Provincia with a managed instance of Departamento.
        //    Fetching ensures it exists and gets us the entity managed by the persistence context.
        Departamento departamento = departamentoRepository.findById(entity.getDepartamento().getId())
                .orElseThrow(() -> new ResourceNotFoundException("error.resource.not.found", entity.getDepartamento().getId()));

        entity.setId(id);
        entity.setDepartamento(departamento);
        return provinciaRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        requireNonNull(id, "ID must not be null");
        return provinciaRepository.findById(id)
                .map(provincia -> {
                    provinciaRepository.delete(provincia);
                    return true;
                })
                .orElse(false);
    }
}

//
//    @Override
//    public Provincia getProvincia(Integer idProvincia) {
//        checkNotNull(idProvincia);
//        Optional<Provincia> provinciaOptional = provinciaRepository.findById(idProvincia);
//        if (provinciaOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, idProvincia);
//        }
//        return provinciaOptional.get();
//    }
//
//    @Override
//    public Provincia addProvincia(Provincia provincia) {
//        checkNotNull(provincia);
//        checkNotNull(provincia.getDepartamento());
//        Optional<Departamento> departamentoOptional = departamentoRepository.findById(provincia.getDepartamento().getIdDepartamento());
//        if (departamentoOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, provincia.getDepartamento().getIdDepartamento());
//        }
//        provincia.setDepartamento(departamentoOptional.get());
//        return provinciaRepository.save(provincia);
//    }
//
//    @Override
//    public Provincia updateProvincia(Integer idProvincia, Provincia provincia) {
//        checkNotNull(idProvincia);
//        checkNotNull(provincia.getDepartamento());
//
//        Optional<Provincia> provinciaOptional = provinciaRepository.findById(idProvincia);
//        if (provinciaOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, idProvincia);
//        }
//        Optional<Departamento> departamentoOptional = departamentoRepository.findById(provincia.getDepartamento().getIdDepartamento());
//        if (departamentoOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, provincia.getDepartamento().getIdDepartamento());
//        }
//        provincia.setDepartamento(departamentoOptional.get());
//        return provinciaRepository.save(provincia);
//    }
//
//    @Override
//    public void deleteProvincia(Integer idProvincia) {
//        checkNotNull(idProvincia);
//        Optional<Provincia> provinciaOptional = provinciaRepository.findById(idProvincia);
//        if (provinciaOptional.isEmpty()) {
//            return;
//        }
//        provinciaRepository.delete(provinciaOptional.get());
//    }
//
//    @Override
//    public Page<Provincia> getProvincias(ProvinciaSearchDto provinciaSearchDto, Integer pageNumber, Integer pageSize, String sort) {
//        checkNotNull(provinciaSearchDto);
//        var sortById = AppConstants.DEFAULT_SORT_BY + Provincia.class.getSimpleName();
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, AppUtils.getSort(sort, sortById));
//        return provinciaRepository.getProvincias(provinciaSearchDto, pageable);
//    }
//
//    @Override
//    public List<Provincia> findAllProvincias() {
//        return provinciaRepository.findAll();
//    }

