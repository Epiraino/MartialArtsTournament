package ep.martialartstournament.martialartstournament.repos;

import ep.martialartstournament.martialartstournament.domain.LiveStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveStreamRepository extends JpaRepository<LiveStream, Integer> {
}
