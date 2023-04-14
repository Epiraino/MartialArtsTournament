package ep.martialartstournament.martialartstournament.repos;

import ep.martialartstournament.martialartstournament.domain.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {
}
