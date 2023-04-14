package ep.martialartstournament.martialartstournament.repos;

import ep.martialartstournament.martialartstournament.domain.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {
}
