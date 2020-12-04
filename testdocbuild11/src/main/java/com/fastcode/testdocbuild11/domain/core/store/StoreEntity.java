package com.fastcode.testdocbuild11.domain.core.store;

import com.fastcode.testdocbuild11.domain.core.abstractentity.AbstractEntity;
import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "store")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class StoreEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    @Basic
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToOne
    @JoinColumn(name = "manager_staff_id")
    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;
}
