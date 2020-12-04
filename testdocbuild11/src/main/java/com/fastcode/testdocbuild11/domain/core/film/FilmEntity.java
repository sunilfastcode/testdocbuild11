package com.fastcode.testdocbuild11.domain.core.film;

import com.fastcode.testdocbuild11.domain.core.abstractentity.AbstractEntity;
import com.fastcode.testdocbuild11.domain.core.language.LanguageEntity;
import java.math.BigDecimal;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "film")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class FilmEntity extends AbstractEntity {

    @Basic
    @Column(name = "rental_rate", nullable = false)
    private BigDecimal rentalRate;

    @Basic
    @Column(name = "rental_duration", nullable = false)
    private Short rentalDuration;

    @Basic
    @Column(name = "length", nullable = true)
    private Short length;

    @Basic
    @Column(name = "rating", nullable = true)
    private String rating;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "replacement_cost", nullable = false)
    private BigDecimal replacementCost;

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", nullable = false)
    private Integer filmId;

    @Basic
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @Basic
    @Column(name = "release_year", nullable = true)
    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageEntity language;
}
