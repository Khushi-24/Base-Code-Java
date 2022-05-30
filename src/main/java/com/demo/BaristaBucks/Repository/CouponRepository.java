package com.demo.BaristaBucks.Repository;

import com.demo.BaristaBucks.Entity.Coffee;
import com.demo.BaristaBucks.Entity.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, Long> {

    boolean existsByCouponName(String couponName);

    @Query(value = "SELECT c.id, c.created_date, c.deleted_date, c.updated_date, c.coupon_name, c.discount_percentage, c.minimum_order_amount FROM " +
            "coupons as c Left JOIN order_details as o " +
            "On c.id = o.coupon_id " +
            "WHERE o.user_id= ?1", nativeQuery = true)
    List<Coupons> findByUserId(Long userId);
}
