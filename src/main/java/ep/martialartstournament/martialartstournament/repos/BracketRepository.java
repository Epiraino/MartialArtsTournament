package ep.martialartstournament.martialartstournament.repos;

import ep.martialartstournament.martialartstournament.domain.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BracketRepository extends JpaRepository<Bracket, Integer> {
}
