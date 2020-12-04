package com.fastcode.testdocbuild11.application.core.actor.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateActorOutput {

    private Integer actorId;
    private String firstName;
    private String lastName;
    private LocalDateTime lastUpdate;
}
