package com.fastcode.testdocbuild11.domain.core.address;

import com.fastcode.testdocbuild11.domain.core.abstractentity.AbstractEntity;
import com.fastcode.testdocbuild11.domain.core.city.CityEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class AddressEntity extends AbstractEntity {

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Basic
    @Column(name = "address2", nullable = true, length = 50)
    private String address2;

    @Basic
    @Column(name = "address3", nullable = true, length = 50)
    private String address3;

    @Basic
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Basic
    @Column(name = "district", nullable = false, length = 20)
    private String district;

    @Basic
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @Basic
    @Column(name = "postal_code", nullable = true, length = 10)
    private String postalCode;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;
}
