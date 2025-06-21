package com.amachi.app.vitalia.country.repository;

import com.amachi.app.vitalia.country.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {

//    /**
//     * Busca países usando una especificación personalizada con paginación.
//     *
//     * @param countrySearchDto Datos de búsqueda.
//     * @param pageable Paginación.
//     * @return Página con los resultados filtrados.
//     */
//    Page<Country> findAll(Specification<Country> specification, Pageable pageable);

    /**
     * Busca países por nombre utilizando una búsqueda por patrón.
     *
     * @param name Nombre del país a buscar.
     * @param pageable Paginación.
     * @return Página con los resultados filtrados por nombre.
     */
    Page<Country> findByNameContainingIgnoreCase(String name, Pageable pageable);

//    default Page<Country> getCountries(CountrySearchDto countrySearchDTO, Pageable pageable) {
//        return findAll(new CountrySpecification(countrySearchDTO), pageable);
//    }
}
