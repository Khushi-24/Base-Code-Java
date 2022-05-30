package com.demo.BaristaBucks.Repository;

import com.demo.BaristaBucks.Entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    Coffee findByNameAndSize(String name, String size);

    List<Coffee> findAllByIsFeatured(boolean b);

    @Query(value = "SELECT c.id, c.created_date, c.updated_date, c.deleted_date, c.description, c.image, c.is_featured, c.name, c.price, c.size FROM my_cart AS mc LEFT JOIN coffee as c on c.id = mc.coffee_id WHERE mc.user_id = ?1 and mc.order_id is NOT Null", nativeQuery = true)
    List<Coffee> findByPastOrder(Long userId);
}
