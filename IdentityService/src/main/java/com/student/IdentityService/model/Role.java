
package com.student.IdentityService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_roles_name",
        columnNames = "name"
    )
)
@Getter 
@Setter 
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "roles_privileges",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Set<Privilege> privileges = new HashSet<>();

    public Role(String name, Set<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges == null
            ? new HashSet<>()
            : new HashSet<>(privileges);
    }

    public Role(Long id, String name, Set<Privilege> privileges) {
        this.id = id;
        this.name = name;
        this.privileges = privileges == null
            ? new HashSet<>()
            : new HashSet<>(privileges);
    }
}
