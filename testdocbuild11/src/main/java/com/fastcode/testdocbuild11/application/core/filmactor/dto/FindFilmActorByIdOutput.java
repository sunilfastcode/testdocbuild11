package com.fastcode.testdocbuild11.application.core.filmactor.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindFilmActorByIdOutput {

    private Short actorId;
    private Short filmId;
    private LocalDateTime lastUpdate;
    private Integer actorDescriptiveField;
    private Integer filmDescriptiveField;
    private Long versiono;
}
