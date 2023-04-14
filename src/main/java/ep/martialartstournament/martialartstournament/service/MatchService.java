package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.Division;
import ep.martialartstournament.martialartstournament.domain.Fighter;
import ep.martialartstournament.martialartstournament.domain.Match;
import ep.martialartstournament.martialartstournament.domain.Referee;
import ep.martialartstournament.martialartstournament.model.MatchDTO;
import ep.martialartstournament.martialartstournament.repos.DivisionRepository;
import ep.martialartstournament.martialartstournament.repos.FighterRepository;
import ep.martialartstournament.martialartstournament.repos.MatchRepository;
import ep.martialartstournament.martialartstournament.repos.RefereeRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final FighterRepository fighterRepository;
    private final RefereeRepository refereeRepository;
    private final DivisionRepository divisionRepository;

    public MatchService(final MatchRepository matchRepository,
            final FighterRepository fighterRepository, final RefereeRepository refereeRepository,
            final DivisionRepository divisionRepository) {
        this.matchRepository = matchRepository;
        this.fighterRepository = fighterRepository;
        this.refereeRepository = refereeRepository;
        this.divisionRepository = divisionRepository;
    }

    public List<MatchDTO> findAll() {
        final List<Match> matchs = matchRepository.findAll(Sort.by("id"));
        return matchs.stream()
                .map((match) -> mapToDTO(match, new MatchDTO()))
                .toList();
    }

    public MatchDTO get(final Integer id) {
        return matchRepository.findById(id)
                .map(match -> mapToDTO(match, new MatchDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MatchDTO matchDTO) {
        final Match match = new Match();
        mapToEntity(matchDTO, match);
        return matchRepository.save(match).getId();
    }

    public void update(final Integer id, final MatchDTO matchDTO) {
        final Match match = matchRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(matchDTO, match);
        matchRepository.save(match);
    }

    public void delete(final Integer id) {
        matchRepository.deleteById(id);
    }

    private MatchDTO mapToDTO(final Match match, final MatchDTO matchDTO) {
        matchDTO.setId(match.getId());
        matchDTO.setTime(match.getTime());
        matchDTO.setLocation(match.getLocation());
        matchDTO.setStatus(match.getStatus());
        matchDTO.setFighter1Id(match.getFighter1Id() == null ? null : match.getFighter1Id().getId());
        matchDTO.setFighter2Id(match.getFighter2Id() == null ? null : match.getFighter2Id().getId());
        matchDTO.setReferee(match.getReferee() == null ? null : match.getReferee().getId());
        matchDTO.setDivision(match.getDivision() == null ? null : match.getDivision().getId());
        return matchDTO;
    }

    private Match mapToEntity(final MatchDTO matchDTO, final Match match) {
        match.setTime(matchDTO.getTime());
        match.setLocation(matchDTO.getLocation());
        match.setStatus(matchDTO.getStatus());
        final Fighter fighter1Id = matchDTO.getFighter1Id() == null ? null : fighterRepository.findById(matchDTO.getFighter1Id())
                .orElseThrow(() -> new NotFoundException("fighter1Id not found"));
        match.setFighter1Id(fighter1Id);
        final Fighter fighter2Id = matchDTO.getFighter2Id() == null ? null : fighterRepository.findById(matchDTO.getFighter2Id())
                .orElseThrow(() -> new NotFoundException("fighter2Id not found"));
        match.setFighter2Id(fighter2Id);
        final Referee referee = matchDTO.getReferee() == null ? null : refereeRepository.findById(matchDTO.getReferee())
                .orElseThrow(() -> new NotFoundException("referee not found"));
        match.setReferee(referee);
        final Division division = matchDTO.getDivision() == null ? null : divisionRepository.findById(matchDTO.getDivision())
                .orElseThrow(() -> new NotFoundException("division not found"));
        match.setDivision(division);
        return match;
    }

}
