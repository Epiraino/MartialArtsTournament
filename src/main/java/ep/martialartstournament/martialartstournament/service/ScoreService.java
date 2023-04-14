package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.Score;
import ep.martialartstournament.martialartstournament.domain.Match;
import ep.martialartstournament.martialartstournament.domain.Referee;
import ep.martialartstournament.martialartstournament.model.ScoreDTO;
import ep.martialartstournament.martialartstournament.repos.MatchRepository;
import ep.martialartstournament.martialartstournament.repos.RefereeRepository;
import ep.martialartstournament.martialartstournament.repos.ScoreRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final MatchRepository matchRepository;
    private final RefereeRepository refereeRepository;

    public ScoreService(final ScoreRepository scoreRepository,
            final MatchRepository matchRepository, final RefereeRepository refereeRepository) {
        this.scoreRepository = scoreRepository;
        this.matchRepository = matchRepository;
        this.refereeRepository = refereeRepository;
    }

    public List<ScoreDTO> findAll() {
        final List<Score> scores = scoreRepository.findAll(Sort.by("id"));
        return scores.stream()
                .map((score) -> mapToDTO(score, new ScoreDTO()))
                .toList();
    }

    public ScoreDTO get(final Integer id) {
        return scoreRepository.findById(id)
                .map(score -> mapToDTO(score, new ScoreDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ScoreDTO scoreDTO) {
        final Score score = new Score();
        mapToEntity(scoreDTO, score);
        return scoreRepository.save(score).getId();
    }

    public void update(final Integer id, final ScoreDTO scoreDTO) {
        final Score score = scoreRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scoreDTO, score);
        scoreRepository.save(score);
    }

    public void delete(final Integer id) {
        scoreRepository.deleteById(id);
    }

    private ScoreDTO mapToDTO(final Score score, final ScoreDTO scoreDTO) {
        scoreDTO.setId(score.getId());
        scoreDTO.setFighter1Score(score.getFighter1Score());
        scoreDTO.setFighter2Score(score.getFighter2Score());
        scoreDTO.setMatch(score.getMatch() == null ? null : score.getMatch().getId());
        scoreDTO.setReferee(score.getReferee() == null ? null : score.getReferee().getId());
        return scoreDTO;
    }

    private Score mapToEntity(final ScoreDTO scoreDTO, final Score score) {
        score.setFighter1Score(scoreDTO.getFighter1Score());
        score.setFighter2Score(scoreDTO.getFighter2Score());
        final Match match = scoreDTO.getMatch() == null ? null : matchRepository.findById(scoreDTO.getMatch())
                .orElseThrow(() -> new NotFoundException("match not found"));
        score.setMatch(match);
        final Referee referee = scoreDTO.getReferee() == null ? null : refereeRepository.findById(scoreDTO.getReferee())
                .orElseThrow(() -> new NotFoundException("referee not found"));
        score.setReferee(referee);
        return score;
    }

}
