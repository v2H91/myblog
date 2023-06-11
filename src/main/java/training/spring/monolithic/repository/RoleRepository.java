
package training.spring.monolithic.repository;

import java.util.List;

import training.spring.monolithic.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	List<Role> findByUsers_UserName(String username);

	Role findByName(String string);
	
}
