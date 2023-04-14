package ep.martialartstournament.martialartstournament.repos;

import ep.martialartstournament.martialartstournament.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
