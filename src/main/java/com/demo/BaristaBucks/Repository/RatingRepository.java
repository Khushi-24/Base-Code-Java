package com.demo.BaristaBucks.Repository;

import com.demo.BaristaBucks.Entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = "SELECT AVG(rating) FROM rating as r LEFT JOIN coffee as c On c.id = r.coffee_id WHERE c.id = ?1", nativeQuery = true)
    Float findRatingByCoffeeId(Long coffeeId);

}
