package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.Division;
import ep.martialartstournament.martialartstournament.domain.Fighter;
import ep.martialartstournament.martialartstournament.domain.User;
import ep.martialartstournament.martialartstournament.model.FighterDTO;
import ep.martialartstournament.martialartstournament.repos.DivisionRepository;
import ep.martialartstournament.martialartstournament.repos.FighterRepository;
import ep.martialartstournament.martialartstournament.repos.UserRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FighterService {

    private final FighterRepository fighterRepository;
    private final UserRepository userRepository;
    private final DivisionRepository divisionRepository;

    public FighterService(final FighterRepository fighterRepository,
            final UserRepository userRepository, final DivisionRepository divisionRepository) {
        this.fighterRepository = fighterRepository;
        this.userRepository = userRepository;
        this.divisionRepository = divisionRepository;
    }

    public List<FighterDTO> findAll() {
        final List<Fighter> fighters = fighterRepository.findAll(Sort.by("id"));
        return fighters.stream()
                .map((fighter) -> mapToDTO(fighter, new FighterDTO()))
                .toList();
    }

    public FighterDTO get(final Integer id) {
        return fighterRepository.findById(id)
                .map(fighter -> mapToDTO(fighter, new FighterDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FighterDTO fighterDTO) {
        final Fighter fighter = new Fighter();
        mapToEntity(fighterDTO, fighter);
        return fighterRepository.save(fighter).getId();
    }

    public void update(final Integer id, final FighterDTO fighterDTO) {
        final Fighter fighter = fighterRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fighterDTO, fighter);
        fighterRepository.save(fighter);
    }

    public void delete(final Integer id) {
        fighterRepository.deleteById(id);
    }

    private FighterDTO mapToDTO(final Fighter fighter, final FighterDTO fighterDTO) {
        fighterDTO.setId(fighter.getId());
        fighterDTO.setName(fighter.getName());
        fighterDTO.setAge(fighter.getAge());
        fighterDTO.setWeight(fighter.getWeight());
        fighterDTO.setBeltRank(fighter.getBeltRank());
        fighterDTO.setContactInformation(fighter.getContactInformation());
        fighterDTO.setUser(fighter.getUser() == null ? null : fighter.getUser().getId());
        fighterDTO.setDivision(fighter.getDivision() == null ? null : fighter.getDivision().getId());
        return fighterDTO;
    }

    private Fighter mapToEntity(final FighterDTO fighterDTO, final Fighter fighter) {
        fighter.setName(fighterDTO.getName());
        fighter.setAge(fighterDTO.getAge());
        fighter.setWeight(fighterDTO.getWeight());
        fighter.setBeltRank(fighterDTO.getBeltRank());
        fighter.setContactInformation(fighterDTO.getContactInformation());
        final User user = fighterDTO.getUser() == null ? null : userRepository.findById(fighterDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        fighter.setUser(user);
        final Division division = fighterDTO.getDivision() == null ? null : divisionRepository.findById(fighterDTO.getDivision())
                .orElseThrow(() -> new NotFoundException("division not found"));
        fighter.setDivision(division);
        return fighter;
    }

}
