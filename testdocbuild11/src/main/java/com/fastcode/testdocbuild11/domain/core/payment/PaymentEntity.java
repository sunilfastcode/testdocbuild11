package com.fastcode.testdocbuild11.domain.core.payment;

import com.fastcode.testdocbuild11.domain.core.abstractentity.AbstractEntity;
import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import java.math.BigDecimal;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class PaymentEntity extends AbstractEntity {

    @Basic
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Integer paymentId;

    @Basic
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private RentalEntity rental;
}
