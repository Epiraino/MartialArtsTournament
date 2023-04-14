package ep.martialartstournament.martialartstournament.repos;

import ep.martialartstournament.martialartstournament.domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
}
