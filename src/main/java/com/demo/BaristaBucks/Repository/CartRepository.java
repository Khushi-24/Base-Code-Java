package com.demo.BaristaBucks.Repository;

import com.demo.BaristaBucks.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByCoffeeIdAndUserId(Long coffeeId, Long userId);

    @Query(value = "SELECT id, created_date, deleted_date, updated_date, coffee_id, order_id, quantity, total_price, user_id FROM my_cart WHERE order_id is null And user_id =?1", nativeQuery = true)
    List<Cart> findByUserId(Long id);

    @Query(value = "SELECT COUNT(*) FROM  my_cart as mc LEFT JOIN coffee AS c ON mc.coffee_id = c.id where c.id = ?1 and mc.order_id IS Not Null", nativeQuery = true)
    Long countByCoffeeIdAndOrderIdNotNull(Long coffeeId);
}
