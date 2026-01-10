package com.amachi.app.core.geography.country.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.geography.country.dto.CountryDto;
import com.amachi.app.core.geography.country.dto.search.CountrySearchDto;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.country.mapper.CountryMapper;
import com.amachi.app.core.geography.country.service.impl.CountryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
@Slf4j
public class CountryController extends BaseController implements CountryApi {

    private final CountryServiceImpl service;
    private final CountryMapper mapper;

    @Override
    public ResponseEntity<CountryDto> getCountryById(Long id) {
        Country entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    public ResponseEntity<CountryDto> createCountry(CountryDto dto) {
        Country entity = mapper.toEntity(dto);
        Country savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CountryDto> updateCountry(Long id, CountryDto dto) {
        Country existingEntity = service.getById(id);
        mapper.updateEntityFromDto(dto, existingEntity);
        Country updatedEntity = service.update(id, existingEntity);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @Override
    public ResponseEntity<Void> deleteCountry(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<Country> entities = service.getAll();
        List<CountryDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<CountryDto>> getPaginatedCountries(CountrySearchDto countrySearchDto,
            Integer pageIndex, Integer pageSize) {
        Page<Country> page = service.getAll(countrySearchDto, pageIndex, pageSize);
        List<CountryDto> dtos = page.getContent()
                .stream()
                .map(country -> mapper.toDto(country)).toList();

        PageResponseDto<CountryDto> response = PageResponseDto.<CountryDto>builder()
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
}
