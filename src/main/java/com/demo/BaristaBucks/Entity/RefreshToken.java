package com.demo.BaristaBucks.Entity;


import com.demo.BaristaBucks.Common.Entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Where(clause = "deleted_date IS NULL")
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long refreshDate;

    @Column
    private String platformType;

    public RefreshToken(String token, String userId, Long refreshDate, String platformType) {
        this.token = token;
        this.userId = userId;
        this.refreshDate = refreshDate;
        this.platformType = platformType;
    }
}
