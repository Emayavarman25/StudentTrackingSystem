
package com.student.IdentityService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
    name = "membership_roles",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_membership_role",
        columnNames = {"membership_id", "role_id"}
    )
)
@Getter
@Setter
@NoArgsConstructor
public class MembershipRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "membership_id", nullable = false)
    private OrganizationMembership membership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false, updatable = false)
    private Instant assignedAt = Instant.now();

    public MembershipRole(
            OrganizationMembership membership,
            Role role) {
        this.membership = membership;
        this.role = role;
    }
}
