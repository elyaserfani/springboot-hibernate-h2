package com.simplespringboot.app.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 5 , max = 50)
    private String name;

    @Column(nullable = false)
    @Size(min = 10, max = 1000)
    private String description;

    @Column(nullable = false)
    @Min(1)
    private Integer shaparakCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
}