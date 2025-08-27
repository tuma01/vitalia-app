package com.amachi.app.vitalia.room.dto;

import com.amachi.app.vitalia.common.utils.TypeRoomEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Schema(name = "Room", description = "Schema to hold Room information")
public class RoomDto {

    @Schema(description = "Unique identifier of the room", example = "1")
    private Long id;

    @Schema(description = "Name of the room", example = "Room 101")
    private Integer numberRoom;

    @Schema(description = "Capacity of the room", example = "2")
    private Integer capacity;

    @Schema(description = "Indicates if the room is private", example = "false")
    private Boolean privateRoom = false;

    @Schema(description = "Type of the room", example = "ESTANDAR")
    private TypeRoomEnum typeRoom = TypeRoomEnum.ESTANDAR;

    @Schema(description = "Number of beds in the room", example = "2")
    private Integer numberOfBeds;

    @Schema(description = "Floor of the block where the room is located", example = "3")
    private Integer blockFloor;

    @Schema(description = "Code of the block where the room is located", example = "B3")
    private String blockCode;

    @Schema(description = "Indicates if the room is unavailable", example = "false")
    private Boolean unavailable;

    @Schema(description = "Hospital where the room is located")
    private Long hospitalId;

    @Schema(description = "Set of patients assigned to the room")
    private Set<Long> patientsIds;

    @Schema(description = "Set of hospitalizations associated with the room")
    private Set<Long> hospitalizacionesIds;
}
