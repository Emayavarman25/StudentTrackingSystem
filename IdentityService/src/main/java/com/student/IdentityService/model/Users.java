
package com.student.IdentityService.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_users_email",
            columnNames = "email"
        ),
        @UniqueConstraint(
            name = "uk_users_mobile",
            columnNames = "mobile"
        )
    }
)
@Getter
@Setter
@Data
@NoArgsConstructor
public class Users  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String mobile;
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    // Temporary: retained until membership-based
    // role assignment is implemented.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    

}
