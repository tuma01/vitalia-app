package com.amachi.app.vitalia.hospital.mapper;

import com.amachi.app.vitalia.hospital.entity.Hospital;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface HospitalMapperSupport {

    // De Set<Long> -> Set<Hospital>
    @Named("hospitalesIdsToHospitalSet")
    default Set<Hospital> hospitalesIdsToHospitalSet(Set<Long> hospitalesIds) {
        if (hospitalesIds == null) return null;
        return hospitalesIds.stream()
                .map(id -> Hospital.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    // De Set<Hospital> -> Set<Long>
    @Named("hospitalToHospitalesIdsSet")
    default Set<Long> hospitalToHospitalesIdsSet(Set<Hospital> hospitales) {
        if (hospitales == null) return null;
        return hospitales.stream()
                .map(Hospital::getId)
                .collect(Collectors.toSet());
    }
}
