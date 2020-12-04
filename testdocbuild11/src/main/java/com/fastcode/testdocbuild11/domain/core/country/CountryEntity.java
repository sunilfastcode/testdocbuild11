package com.fastcode.testdocbuild11.domain.core.country;

import com.fastcode.testdocbuild11.domain.core.abstractentity.AbstractEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CountryEntity extends AbstractEntity {

    @Basic
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private Integer countryId;

    @Basic
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
