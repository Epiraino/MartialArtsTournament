package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.LiveStream;
import ep.martialartstournament.martialartstournament.domain.Match;
import ep.martialartstournament.martialartstournament.model.LiveStreamDTO;
import ep.martialartstournament.martialartstournament.repos.LiveStreamRepository;
import ep.martialartstournament.martialartstournament.repos.MatchRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LiveStreamService {

    private final LiveStreamRepository liveStreamRepository;
    private final MatchRepository matchRepository;

    public LiveStreamService(final LiveStreamRepository liveStreamRepository,
            final MatchRepository matchRepository) {
        this.liveStreamRepository = liveStreamRepository;
        this.matchRepository = matchRepository;
    }

    public List<LiveStreamDTO> findAll() {
        final List<LiveStream> liveStreams = liveStreamRepository.findAll(Sort.by("id"));
        return liveStreams.stream()
                .map((liveStream) -> mapToDTO(liveStream, new LiveStreamDTO()))
                .toList();
    }

    public LiveStreamDTO get(final Integer id) {
        return liveStreamRepository.findById(id)
                .map(liveStream -> mapToDTO(liveStream, new LiveStreamDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LiveStreamDTO liveStreamDTO) {
        final LiveStream liveStream = new LiveStream();
        mapToEntity(liveStreamDTO, liveStream);
        return liveStreamRepository.save(liveStream).getId();
    }

    public void update(final Integer id, final LiveStreamDTO liveStreamDTO) {
        final LiveStream liveStream = liveStreamRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(liveStreamDTO, liveStream);
        liveStreamRepository.save(liveStream);
    }

    public void delete(final Integer id) {
        liveStreamRepository.deleteById(id);
    }

    private LiveStreamDTO mapToDTO(final LiveStream liveStream, final LiveStreamDTO liveStreamDTO) {
        liveStreamDTO.setId(liveStream.getId());
        liveStreamDTO.setUrl(liveStream.getUrl());
        liveStreamDTO.setStatus(liveStream.getStatus());
        liveStreamDTO.setMatch(liveStream.getMatch() == null ? null : liveStream.getMatch().getId());
        return liveStreamDTO;
    }

    private LiveStream mapToEntity(final LiveStreamDTO liveStreamDTO, final LiveStream liveStream) {
        liveStream.setUrl(liveStreamDTO.getUrl());
        liveStream.setStatus(liveStreamDTO.getStatus());
        final Match match = liveStreamDTO.getMatch() == null ? null : matchRepository.findById(liveStreamDTO.getMatch())
                .orElseThrow(() -> new NotFoundException("match not found"));
        liveStream.setMatch(match);
        return liveStream;
    }

}
