package com.github.kshashov.translates.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "permissions", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "permissions_unique_code", columnNames = "code")
})
public class Permission implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", unique = true)
    private String code;
}
