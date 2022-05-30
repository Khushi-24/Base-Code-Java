package com.demo.BaristaBucks.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "coffee_id", insertable = false, updatable = false, nullable = false)
    private Long coffeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id", referencedColumnName = "id", nullable = false)
    private Coffee coffee;

    @Column
    private Float rating;

}
