package com.demo.BaristaBucks.Entity;

import com.demo.BaristaBucks.Common.Entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_date IS NULL")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column
    private Double totalPrice;

    @Column
    private Double discountedPrice;

    @Column(name = "coupon_id", insertable = false, updatable = false, nullable = false)
    private Long couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id", nullable = false)
    private Coupons coupon;

    @Column
    private Double paidPrice;

}
