package com.student.IdentityService.model;

import java.util.HashSet;
import java.util.Set;

import com.student.IdentityService.model.Enum.MembershipStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "organization_memberships",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_membership_user_org",
            columnNames = {"user_id", "organization_id"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class OrganizationMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status = MembershipStatus.PENDING;

    @OneToMany(
        mappedBy = "membership",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<MembershipRole> membershipRoles = new HashSet<>();
}