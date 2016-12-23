package gov.diski.ResourceServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.diski.ResourceServer.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
