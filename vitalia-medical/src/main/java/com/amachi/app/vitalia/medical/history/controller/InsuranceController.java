package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.vitalia.medical.history.dto.InsuranceDto;
import com.amachi.app.vitalia.medical.history.dto.search.InsuranceSearchDto;
import com.amachi.app.vitalia.medical.history.entity.Insurance;
import com.amachi.app.vitalia.medical.history.mapper.InsuranceMapper;
import com.amachi.app.vitalia.medical.history.service.InsuranceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(AppConstants.Url.API_V1 + "/insurances")
public class InsuranceController extends BaseController implements InsuranceApi {

    private final InsuranceService service;
    private final InsuranceMapper mapper;

    public InsuranceController(InsuranceService service, InsuranceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<InsuranceDto> getInsuranceById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<InsuranceDto> createInsurance(InsuranceDto dto) {
        Insurance entity = mapper.toEntity(dto);
        Insurance saved = service.create(entity);
        return ResponseEntity.created(URI.create(AppConstants.Url.API_V1 + "/insurances/" + saved.getId()))
                .body(mapper.toDto(saved));
    }

    @Override
    public ResponseEntity<InsuranceDto> updateInsurance(Long id, InsuranceDto dto) {
        Insurance entity = mapper.toEntity(dto);
        return ResponseEntity.ok(mapper.toDto(service.update(id, entity)));
    }

    @Override
    public ResponseEntity<Void> deleteInsurance(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<InsuranceDto>> getAllInsurances() {
        return ResponseEntity.ok(mapper.toDtoList(service.getAll()));
    }

    @Override
    public ResponseEntity<PageResponseDto<InsuranceDto>> getPaginatedInsurances(InsuranceSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Insurance> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<InsuranceDto>builder()
                .content(mapper.toDtoList(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .build());
    }
}
