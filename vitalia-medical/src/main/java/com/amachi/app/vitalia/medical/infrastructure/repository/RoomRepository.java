package com.amachi.app.vitalia.medical.infrastructure.repository;

import com.amachi.app.vitalia.medical.infrastructure.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestion de habitaciones y boxes.
 */
@Repository
public interface RoomRepository extends CommonRepository<Room, Long> {

    /**
     * Busca habitaciones por su numero identificador.
     *
     * @param roomNumber Numero de la habitacion.
     * @param pageable Paginacion.
     * @return Pagina de habitaciones.
     */
    Page<Room> findByRoomNumberContainingIgnoreCase(String roomNumber, Pageable pageable);
}
