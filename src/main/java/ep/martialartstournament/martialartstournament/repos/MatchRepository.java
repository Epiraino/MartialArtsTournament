package ep.martialartstournament.martialartstournament.repos;

import ep.martialartstournament.martialartstournament.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
}
