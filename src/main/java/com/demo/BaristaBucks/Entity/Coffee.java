package com.demo.BaristaBucks.Entity;

import com.demo.BaristaBucks.Common.Entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Where(clause = "deleted_date IS NULL")
@NoArgsConstructor
public class Coffee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String description;

    @Column
    private String image;

    @Column
    private String size;

    @ColumnDefault(value = "0")
    private Boolean isFeatured = false;

}
