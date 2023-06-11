package training.spring.monolithic.repository;

import training.spring.monolithic.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByUserName(String userName);
    Optional<Users> findByEmail(String userName);
}
