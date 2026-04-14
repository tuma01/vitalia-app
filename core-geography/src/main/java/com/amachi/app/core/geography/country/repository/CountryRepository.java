package com.amachi.app.core.geography.country.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.geography.country.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CommonRepository<Country, Long> {

    /**
     * Search countries by name using pattern matching.
     *
     * @param name     Name of the country to search.
     * @param pageable Pagination details.
     * @return Page of results filtered by name.
     */
    Page<Country> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
