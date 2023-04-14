package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.Referee;
import ep.martialartstournament.martialartstournament.domain.User;
import ep.martialartstournament.martialartstournament.model.RefereeDTO;
import ep.martialartstournament.martialartstournament.repos.RefereeRepository;
import ep.martialartstournament.martialartstournament.repos.UserRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RefereeService {

    private final RefereeRepository refereeRepository;
    private final UserRepository userRepository;

    public RefereeService(final RefereeRepository refereeRepository,
            final UserRepository userRepository) {
        this.refereeRepository = refereeRepository;
        this.userRepository = userRepository;
    }

    public List<RefereeDTO> findAll() {
        final List<Referee> referees = refereeRepository.findAll(Sort.by("id"));
        return referees.stream()
                .map((referee) -> mapToDTO(referee, new RefereeDTO()))
                .toList();
    }

    public RefereeDTO get(final Integer id) {
        return refereeRepository.findById(id)
                .map(referee -> mapToDTO(referee, new RefereeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final RefereeDTO refereeDTO) {
        final Referee referee = new Referee();
        mapToEntity(refereeDTO, referee);
        return refereeRepository.save(referee).getId();
    }

    public void update(final Integer id, final RefereeDTO refereeDTO) {
        final Referee referee = refereeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(refereeDTO, referee);
        refereeRepository.save(referee);
    }

    public void delete(final Integer id) {
        refereeRepository.deleteById(id);
    }

    private RefereeDTO mapToDTO(final Referee referee, final RefereeDTO refereeDTO) {
        refereeDTO.setId(referee.getId());
        refereeDTO.setName(referee.getName());
        refereeDTO.setCertificationLevel(referee.getCertificationLevel());
        refereeDTO.setContactInformation(referee.getContactInformation());
        refereeDTO.setUser(referee.getUser() == null ? null : referee.getUser().getId());
        return refereeDTO;
    }

    private Referee mapToEntity(final RefereeDTO refereeDTO, final Referee referee) {
        referee.setName(refereeDTO.getName());
        referee.setCertificationLevel(refereeDTO.getCertificationLevel());
        referee.setContactInformation(refereeDTO.getContactInformation());
        final User user = refereeDTO.getUser() == null ? null : userRepository.findById(refereeDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        referee.setUser(user);
        return referee;
    }

}
