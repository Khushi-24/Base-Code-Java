package com.demo.BaristaBucks.Entity;

import com.demo.BaristaBucks.Common.Entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_date IS NULL")
public class Coupons extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String couponName;

    private Long discountPercentage;

    private Long minimumOrderAmount;

    private Long maxDiscountPrice;
}
