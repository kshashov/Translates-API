package com.github.kshashov.translates.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "tags", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tags_unique_title", columnNames = "title")})
public class Tag implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;
}
