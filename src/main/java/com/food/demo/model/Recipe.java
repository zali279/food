package com.food.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name ="recipes")
@Getter
@Setter
public class Recipe {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id ;

    @Column
    private  String name ;

    @Column
    private String time ;

    @Column
    private Integer portions ;

    @Column
    private String ingredients;

    @Column
    private String steps ;

    @Column
    private Boolean isPublic ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonIgnore
        private User user;

}
