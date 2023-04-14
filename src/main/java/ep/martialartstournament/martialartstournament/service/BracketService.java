package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.Bracket;
import ep.martialartstournament.martialartstournament.domain.Division;
import ep.martialartstournament.martialartstournament.domain.Fighter;
import ep.martialartstournament.martialartstournament.domain.Match;
import ep.martialartstournament.martialartstournament.model.BracketDTO;
import ep.martialartstournament.martialartstournament.repos.BracketRepository;
import ep.martialartstournament.martialartstournament.repos.DivisionRepository;
import ep.martialartstournament.martialartstournament.repos.FighterRepository;
import ep.martialartstournament.martialartstournament.repos.MatchRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BracketService {

    private final BracketRepository bracketRepository;
    private final MatchRepository matchRepository;
    private final DivisionRepository divisionRepository;
    private final FighterRepository fighterRepository;

    public BracketService(final BracketRepository bracketRepository,
            final MatchRepository matchRepository, final DivisionRepository divisionRepository,
            final FighterRepository fighterRepository) {
        this.bracketRepository = bracketRepository;
        this.matchRepository = matchRepository;
        this.divisionRepository = divisionRepository;
        this.fighterRepository = fighterRepository;
    }

    public List<BracketDTO> findAll() {
        final List<Bracket> brackets = bracketRepository.findAll(Sort.by("id"));
        return brackets.stream()
                .map((bracket) -> mapToDTO(bracket, new BracketDTO()))
                .toList();
    }

    public BracketDTO get(final Integer id) {
        return bracketRepository.findById(id)
                .map(bracket -> mapToDTO(bracket, new BracketDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BracketDTO bracketDTO) {
        final Bracket bracket = new Bracket();
        mapToEntity(bracketDTO, bracket);
        return bracketRepository.save(bracket).getId();
    }

    public void update(final Integer id, final BracketDTO bracketDTO) {
        final Bracket bracket = bracketRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bracketDTO, bracket);
        bracketRepository.save(bracket);
    }

    public void delete(final Integer id) {
        bracketRepository.deleteById(id);
    }

    private BracketDTO mapToDTO(final Bracket bracket, final BracketDTO bracketDTO) {
        bracketDTO.setId(bracket.getId());
        bracketDTO.setRoundNumber(bracket.getRoundNumber());
        bracketDTO.setMatch(bracket.getMatch() == null ? null : bracket.getMatch().getId());
        bracketDTO.setDivision(bracket.getDivision() == null ? null : bracket.getDivision().getId());
        bracketDTO.setWinner(bracket.getWinner() == null ? null : bracket.getWinner().getId());
        return bracketDTO;
    }

    private Bracket mapToEntity(final BracketDTO bracketDTO, final Bracket bracket) {
        bracket.setRoundNumber(bracketDTO.getRoundNumber());
        final Match match = bracketDTO.getMatch() == null ? null : matchRepository.findById(bracketDTO.getMatch())
                .orElseThrow(() -> new NotFoundException("match not found"));
        bracket.setMatch(match);
        final Division division = bracketDTO.getDivision() == null ? null : divisionRepository.findById(bracketDTO.getDivision())
                .orElseThrow(() -> new NotFoundException("division not found"));
        bracket.setDivision(division);
        final Fighter winner = bracketDTO.getWinner() == null ? null : fighterRepository.findById(bracketDTO.getWinner())
                .orElseThrow(() -> new NotFoundException("winner not found"));
        bracket.setWinner(winner);
        return bracket;
    }

}
