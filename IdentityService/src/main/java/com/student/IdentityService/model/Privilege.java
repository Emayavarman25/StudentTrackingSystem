
package com.student.IdentityService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "privileges",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_privileges_name",
        columnNames = "name"
    )
)
@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Privilege(String name) {
        this.name = name;
    }

    public Privilege(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
