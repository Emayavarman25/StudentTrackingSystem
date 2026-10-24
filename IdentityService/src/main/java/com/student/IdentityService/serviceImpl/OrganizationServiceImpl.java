package com.student.IdentityService.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.student.IdentityService.model.Enum.MembershipStatus;
import com.student.IdentityService.model.Enum.OrganizationStatus;
import com.student.IdentityService.model.Organization;
import com.student.IdentityService.model.OrganizationMembership;
import com.student.IdentityService.model.Users;
import com.student.IdentityService.repository.OrganizationMembershipRepository;
import com.student.IdentityService.repository.OrganizationRepository;
import com.student.IdentityService.repository.UsersRepo;
import com.student.IdentityService.request.CreateOrganizationRequest;
import com.student.IdentityService.service.OrganizationService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMembershipRepository membershipRepository;
    private final UsersRepo usersRepository;

    public OrganizationServiceImpl(
            OrganizationRepository organizationRepository,
            OrganizationMembershipRepository membershipRepository,
            UsersRepo usersRepository) {
        this.organizationRepository = organizationRepository;
        this.membershipRepository = membershipRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public Organization createOrganization(CreateOrganizationRequest request, Long userId) {
        String organizationName = request.name().trim();
        if (organizationRepository.existsByName(organizationName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Organization with the same name already exists");
        }

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Organization organization = new Organization();
        organization.setName(organizationName);
        organization.setType(request.type());
        organization.setStatus(OrganizationStatus.PENDING);
        Organization savedOrganization = organizationRepository.save(organization);

        OrganizationMembership membership = new OrganizationMembership();
        membership.setUser(user);
        membership.setOrganization(savedOrganization);
        membership.setStatus(MembershipStatus.ACTIVE);
        membershipRepository.save(membership);

        return savedOrganization;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Organization> getOrganizationsForUser(Long userId) {
        return membershipRepository
                .findAllByUser_IdAndStatus(userId, MembershipStatus.ACTIVE)
                .stream()
                .map(OrganizationMembership::getOrganization)
                .toList();
    }
}
