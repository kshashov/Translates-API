package com.github.kshashov.translates.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "languages", uniqueConstraints = {
        @UniqueConstraint(name = "languages_unique_title", columnNames = "title"),
        @UniqueConstraint(name = "languages_unique_code", columnNames = "code")
})
public class Language implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "code")
    private String code;
}
