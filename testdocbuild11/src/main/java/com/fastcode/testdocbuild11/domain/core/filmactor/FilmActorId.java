package com.fastcode.testdocbuild11.domain.core.filmactor;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmActorId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Short actorId;
    private Short filmId;

    public FilmActorId(Short actorId, Short filmId) {
        this.actorId = actorId;
        this.filmId = filmId;
    }
}
