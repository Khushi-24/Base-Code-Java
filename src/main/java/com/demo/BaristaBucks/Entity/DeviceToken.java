package com.demo.BaristaBucks.Entity;

import com.demo.BaristaBucks.Common.Entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_date IS NULL")
public class DeviceToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceTokenId;

    private String deviceToken;

    private String platformType;

    private Long userId;


    public DeviceToken(String deviceToken, String platformType, Long id) {
        this.deviceToken = deviceToken;
        this.platformType = platformType;
        this.userId = id;
    }
}
