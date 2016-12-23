package gov.diski.ResourceServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.diski.ResourceServer.model.User;

/**
 * @author Moritz Schulze
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
