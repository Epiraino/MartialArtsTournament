package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.Tournament;
import ep.martialartstournament.martialartstournament.domain.User;
import ep.martialartstournament.martialartstournament.model.TournamentDTO;
import ep.martialartstournament.martialartstournament.repos.TournamentRepository;
import ep.martialartstournament.martialartstournament.repos.UserRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    public TournamentService(final TournamentRepository tournamentRepository,
            final UserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    public List<TournamentDTO> findAll() {
        final List<Tournament> tournaments = tournamentRepository.findAll(Sort.by("id"));
        return tournaments.stream()
                .map((tournament) -> mapToDTO(tournament, new TournamentDTO()))
                .toList();
    }

    public TournamentDTO get(final Integer id) {
        return tournamentRepository.findById(id)
                .map(tournament -> mapToDTO(tournament, new TournamentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TournamentDTO tournamentDTO) {
        final Tournament tournament = new Tournament();
        mapToEntity(tournamentDTO, tournament);
        return tournamentRepository.save(tournament).getId();
    }

    public void update(final Integer id, final TournamentDTO tournamentDTO) {
        final Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tournamentDTO, tournament);
        tournamentRepository.save(tournament);
    }

    public void delete(final Integer id) {
        tournamentRepository.deleteById(id);
    }

    private TournamentDTO mapToDTO(final Tournament tournament, final TournamentDTO tournamentDTO) {
        tournamentDTO.setId(tournament.getId());
        tournamentDTO.setName(tournament.getName());
        tournamentDTO.setLocation(tournament.getLocation());
        tournamentDTO.setDate(tournament.getDate());
        tournamentDTO.setRules(tournament.getRules());
        tournamentDTO.setAdmin(tournament.getAdmin() == null ? null : tournament.getAdmin().getId());
        return tournamentDTO;
    }

    private Tournament mapToEntity(final TournamentDTO tournamentDTO, final Tournament tournament) {
        tournament.setName(tournamentDTO.getName());
        tournament.setLocation(tournamentDTO.getLocation());
        tournament.setDate(tournamentDTO.getDate());
        tournament.setRules(tournamentDTO.getRules());
        final User admin = tournamentDTO.getAdmin() == null ? null : userRepository.findById(tournamentDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        tournament.setAdmin(admin);
        return tournament;
    }

}
