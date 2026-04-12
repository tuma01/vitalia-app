package com.amachi.app.vitalia.medical.infrastructure.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.RoomSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Room;

/**
 * Interfaz de servicio para la gestion de habitaciones hospitlararias.
 */
public interface RoomService extends GenericService<Room, RoomSearchDto> {
}
