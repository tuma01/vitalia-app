package com.amachi.app.vitalia.doctorprofessionspeciality.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.DoctorProfessionSpecialityDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.search.DoctorProfessionSpecialitySearchDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import com.amachi.app.vitalia.doctorprofessionspeciality.mapper.DoctorProfessionSpecialityMapper;
import com.amachi.app.vitalia.doctorprofessionspeciality.service.impl.DoctorProfessionSpecialityServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/doctorprofessionspecialities")
public class DoctorProfessionSpecialityController extends BaseController implements DoctorProfessionSpecialityApi {

    private DoctorProfessionSpecialityServiceImpl service;
    private DoctorProfessionSpecialityMapper mapper;

    @Override
    public ResponseEntity<DoctorProfessionSpecialityDto> getDoctorProfessionSpecialityById(Long id) {
        Optional<DoctorProfessionSpeciality> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<DoctorProfessionSpecialityDto> createDoctorProfessionSpeciality(DoctorProfessionSpecialityDto dto) {
        DoctorProfessionSpeciality entity = mapper.toEntity(dto);
        DoctorProfessionSpeciality savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DoctorProfessionSpecialityDto> updateDoctorProfessionSpeciality(Long id, DoctorProfessionSpecialityDto dto) {
        DoctorProfessionSpeciality entity = mapper.toEntity(dto);
        DoctorProfessionSpeciality updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteDoctorProfessionSpeciality(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<List<DoctorProfessionSpecialityDto>> getAllDoctorProfessionSpecialities() {
        List<DoctorProfessionSpeciality> entities = service.getAll();
        List<DoctorProfessionSpecialityDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<DoctorProfessionSpecialityDto>> getPaginatedDoctorProfessionSpecialities(DoctorProfessionSpecialitySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<DoctorProfessionSpeciality> page = service.getAll(searchDto, pageIndex, pageSize);
        List<DoctorProfessionSpecialityDto> dtos = page.getContent()
                .stream()
                .map(doctorProfessionSpeciality -> mapper.toDto(doctorProfessionSpeciality)).toList();

        PageResponseDto<DoctorProfessionSpecialityDto> response = PageResponseDto.<DoctorProfessionSpecialityDto>builder()
                .content(dtos)
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .numberOfElements(page.getNumberOfElements())
                .build();

        return ResponseEntity.ok(response);
    }


//    @Override
//    public ResponseEntity<DoctorProfessionSpecialityDto> getDoctorProfessionSpeciality(Integer idDoctorProfessionSpeciality) {
//        final var doctorProfessionSpeciality = doctorProfessionSpecialityService.getDoctorProfessionSpeciality(idDoctorProfessionSpeciality);
//        return new ResponseEntity<>(doctorProfessionSpecialityMapper.toDto(doctorProfessionSpeciality), HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<DoctorProfessionSpecialityDto> addDoctorProfessionSpeciality(DoctorProfessionSpecialityDto doctorProfessionSpecialityDto) {
//        final var doctorProfessionSpeciality = doctorProfessionSpecialityService.addDoctorProfessionSpeciality(doctorProfessionSpecialityMapper.toEntity(doctorProfessionSpecialityDto));
//        return new ResponseEntity<>(doctorProfessionSpecialityMapper.toDto(doctorProfessionSpeciality), HttpStatus.CREATED);
//    }
//
//    @Override
//    public ResponseEntity<DoctorProfessionSpecialityDto> updateDoctorProfessionSpeciality(Integer idDoctorProfessionSpeciality, DoctorProfessionSpecialityDto doctorProfessionSpecialityDto) {
//        var doctorProfessionSpeciality = doctorProfessionSpecialityService.updateDoctorProfessionSpeciality(idDoctorProfessionSpeciality, doctorProfessionSpecialityMapper.toEntity(doctorProfessionSpecialityDto));
//        return new ResponseEntity<>(doctorProfessionSpecialityMapper.toDto(doctorProfessionSpeciality), HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<Object> deleteDoctorProfessionSpeciality(@PathVariable("id") final Integer idDoctorProfessionSpeciality) {
//        doctorProfessionSpecialityService.deleteDoctorProfessionSpeciality(idDoctorProfessionSpeciality);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @Override
//    @Hidden
//    //	@GetMapping(value = "/doctorProfessionSpecialitys", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Page<DoctorProfessionSpecialityDto>> getDoctorProfessionSpecialities(DoctorProfessionSpecialitySearchDto doctorProfessionSpecialitySearchDto, Integer pageNumber, Integer pageSize, String sort) {
//        final Page<DoctorProfessionSpeciality> pageDoctorProfessionSpeciality = doctorProfessionSpecialityService.getDoctorProfessionSpecialities(doctorProfessionSpecialitySearchDto, pageNumber, pageSize, sort);
//        return new ResponseEntity<>(convert(pageDoctorProfessionSpeciality, doctorProfessionSpecialityMapper, uiOrderToEntityOrderPropertyMapper), HttpStatus.OK);
//    }
//
//    @Override
//    //	@GetMapping(value = "/doctorProfessionSpecialitysall", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<DoctorProfessionSpecialityDto>> findAllDoctorProfessionSpecialities() {
//        log.info("/doctorProfessionSpecialitysall request received");
//        List<DoctorProfessionSpeciality> doctorProfessionSpecialities = doctorProfessionSpecialityService.findAllDoctorProfessionSpecialities();
//        List<DoctorProfessionSpecialityDto> doctorProfessionSpecialityDtos = doctorProfessionSpecialities.stream().map(entity -> doctorProfessionSpecialityMapper.toDto(entity)).toList();
//        return ResponseEntity.ok(doctorProfessionSpecialityDtos);
//    }
//
//    @Override
//    protected Class<DoctorProfessionSpeciality> getEntityClass() {
//        return DoctorProfessionSpeciality.class;
//    }
}
