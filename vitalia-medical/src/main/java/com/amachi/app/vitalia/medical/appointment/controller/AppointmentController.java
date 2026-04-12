package com.amachi.app.vitalia.medical.appointment.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.common.enums.AppointmentStatus;
import com.amachi.app.vitalia.medical.appointment.dto.AppointmentDto;
import com.amachi.app.vitalia.medical.appointment.dto.search.AppointmentSearchDto;
import com.amachi.app.vitalia.medical.appointment.entity.Appointment;
import com.amachi.app.vitalia.medical.appointment.mapper.AppointmentMapper;
import com.amachi.app.vitalia.medical.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador operacional para citas medicas de Vitalia.
 */
@RestController
@RequestMapping("/clinical/appointments")
@RequiredArgsConstructor
public class AppointmentController extends BaseController implements AppointmentApi {

    private final AppointmentService service;
    private final AppointmentMapper mapper;

    @Override
    public ResponseEntity<AppointmentDto> getAppointmentById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<AppointmentDto> createAppointment(AppointmentDto dto) {
        Appointment entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AppointmentDto> updateAppointment(Long id, AppointmentDto dto) {
        Appointment existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    public ResponseEntity<PageResponseDto<AppointmentDto>> getPaginatedAppointments(AppointmentSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Appointment> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<AppointmentDto>builder()
                .content(mapper.toDTOs(page.getContent()))
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .numberOfElements(page.getNumberOfElements())
                .build());
    }

    @Override
    public ResponseEntity<AppointmentDto> updateAppointmentStatus(Long id, AppointmentStatus status) {
        return ResponseEntity.ok(mapper.toDto(service.updateStatus(id, status)));
    }
}
