package com.fastcode.testdocbuild11.domain.core.language;

import com.fastcode.testdocbuild11.domain.core.abstractentity.AbstractEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "language")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class LanguageEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", nullable = false)
    private Integer languageId;

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Basic
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
