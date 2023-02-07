package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import recipes.model.User;

import java.util.Optional;
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);
    User findByEmailIgnoreCase(String email);
    @Override
    Optional<User> findById(Long aLong);
    @Override
    boolean existsById(Long aLong);
}
