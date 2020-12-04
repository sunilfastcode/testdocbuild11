package com.fastcode.testdocbuild11.domain.core.filmactor;

import com.fastcode.testdocbuild11.domain.core.abstractentity.AbstractEntity;
import com.fastcode.testdocbuild11.domain.core.actor.ActorEntity;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "film_actor")
@IdClass(FilmActorId.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class FilmActorEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "actor_id", nullable = false)
    private Short actorId;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "film_id", nullable = false)
    private Short filmId;

    @Basic
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "actor_id", insertable = false, updatable = false)
    private ActorEntity actor;

    @ManyToOne
    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    private FilmEntity film;
}
