package com.amachi.app.vitalia.country.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.country.dto.CountryDto;
import com.amachi.app.vitalia.country.dto.search.CountrySearchDto;
import com.amachi.app.vitalia.country.entity.Country;
import com.amachi.app.vitalia.country.mapper.CountryMapper;
import com.amachi.app.vitalia.country.service.impl.CountryServiceImpl;
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
import java.util.Optional;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
@Slf4j
public class CountryController extends BaseController implements CountryApi {

    private final CountryServiceImpl service;
    private final CountryMapper mapper;

    @Override
    public ResponseEntity<CountryDto> getCountryById(Long id) {
        Optional<Country> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());

    }

    @Override
    public ResponseEntity<CountryDto> createCountry(CountryDto dto) {
        Country entity = mapper.toEntity(dto);
        Country savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CountryDto> updateCountry(Long id, CountryDto dto) {
        Country entity = mapper.toEntity(dto);
        Country updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteCountry(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    @Override
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<Country> entities = service.getAll();
        List<CountryDto> dtos =  entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<CountryDto>> getPaginatedCountries(CountrySearchDto countrySearchDto, Integer pageIndex, Integer pageSize) {
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
