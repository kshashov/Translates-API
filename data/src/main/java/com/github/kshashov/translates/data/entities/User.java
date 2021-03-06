package com.github.kshashov.translates.data.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "users_unique_client_sub", columnNames = {"client", "sub"})
})
@NamedEntityGraph(name = "User.permissions",
        attributeNodes = {@NamedAttributeNode(value = "role", subgraph = "Role.permissions")},
        subgraphs = {@NamedSubgraph(name = "Role.permissions", attributeNodes = @NamedAttributeNode("permissions"))})
public class User implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "sub")
    private String sub;

    @NotNull
    @Column(name = "client")
    private String client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}
