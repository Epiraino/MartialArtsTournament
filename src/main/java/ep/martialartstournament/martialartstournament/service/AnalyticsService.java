package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.Analytics;
import ep.martialartstournament.martialartstournament.domain.Tournament;
import ep.martialartstournament.martialartstournament.model.AnalyticsDTO;
import ep.martialartstournament.martialartstournament.repos.AnalyticsRepository;
import ep.martialartstournament.martialartstournament.repos.TournamentRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;
    private final TournamentRepository tournamentRepository;

    public AnalyticsService(final AnalyticsRepository analyticsRepository,
            final TournamentRepository tournamentRepository) {
        this.analyticsRepository = analyticsRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public List<AnalyticsDTO> findAll() {
        final List<Analytics> analyticss = analyticsRepository.findAll(Sort.by("id"));
        return analyticss.stream()
                .map((analytics) -> mapToDTO(analytics, new AnalyticsDTO()))
                .toList();
    }

    public AnalyticsDTO get(final Integer id) {
        return analyticsRepository.findById(id)
                .map(analytics -> mapToDTO(analytics, new AnalyticsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AnalyticsDTO analyticsDTO) {
        final Analytics analytics = new Analytics();
        mapToEntity(analyticsDTO, analytics);
        return analyticsRepository.save(analytics).getId();
    }

    public void update(final Integer id, final AnalyticsDTO analyticsDTO) {
        final Analytics analytics = analyticsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(analyticsDTO, analytics);
        analyticsRepository.save(analytics);
    }

    public void delete(final Integer id) {
        analyticsRepository.deleteById(id);
    }

    private AnalyticsDTO mapToDTO(final Analytics analytics, final AnalyticsDTO analyticsDTO) {
        analyticsDTO.setId(analytics.getId());
        analyticsDTO.setRegisteredFightersCount(analytics.getRegisteredFightersCount());
        analyticsDTO.setCompletedMatchesCount(analytics.getCompletedMatchesCount());
        analyticsDTO.setMatchStatistics(analytics.getMatchStatistics());
        analyticsDTO.setTournament(analytics.getTournament() == null ? null : analytics.getTournament().getId());
        return analyticsDTO;
    }

    private Analytics mapToEntity(final AnalyticsDTO analyticsDTO, final Analytics analytics) {
        analytics.setRegisteredFightersCount(analyticsDTO.getRegisteredFightersCount());
        analytics.setCompletedMatchesCount(analyticsDTO.getCompletedMatchesCount());
        analytics.setMatchStatistics(analyticsDTO.getMatchStatistics());
        final Tournament tournament = analyticsDTO.getTournament() == null ? null : tournamentRepository.findById(analyticsDTO.getTournament())
                .orElseThrow(() -> new NotFoundException("tournament not found"));
        analytics.setTournament(tournament);
        return analytics;
    }

}
