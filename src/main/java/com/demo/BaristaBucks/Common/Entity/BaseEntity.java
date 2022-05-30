package com.demo.BaristaBucks.Common.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdTimeStamp;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", nullable = false)
    private Date updatedTimeStamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date")
    private Date deletedTimeStamp;
}
