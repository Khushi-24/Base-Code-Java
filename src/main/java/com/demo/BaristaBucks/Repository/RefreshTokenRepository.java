package com.demo.BaristaBucks.Repository;

import com.demo.BaristaBucks.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	RefreshToken findByToken(String refreshToken);

//	@Transactional ->?
//	@Modifying(clearAutomatically = true) ->?
	void deleteByUserId(String userId);

	boolean existsByUserId(String userId);

	List<RefreshToken> findByUserId(String userId);
}
