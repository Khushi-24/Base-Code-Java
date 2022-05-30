package com.demo.BaristaBucks.Entity;

import com.demo.BaristaBucks.Common.Entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "my_cart")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_date IS NULL")
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coffee_id", insertable = false, updatable = false, nullable = false)
    private Long coffeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id", referencedColumnName = "id", nullable = false)
    private Coffee coffee;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private Long userId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private Long quantity;

    private Long orderId;

    private Double totalPrice;

}
