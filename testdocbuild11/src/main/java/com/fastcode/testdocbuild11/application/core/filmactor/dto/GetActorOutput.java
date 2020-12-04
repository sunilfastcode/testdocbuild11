package com.fastcode.testdocbuild11.application.core.filmactor.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetActorOutput {

    private Integer actorId;
    private String firstName;
    private String lastName;
    private LocalDateTime lastUpdate;
    private Short filmActorActorId;
    private Short filmActorFilmId;
}
