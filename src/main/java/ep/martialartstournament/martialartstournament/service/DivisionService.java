package ep.martialartstournament.martialartstournament.service;

import ep.martialartstournament.martialartstournament.domain.Division;
import ep.martialartstournament.martialartstournament.domain.Tournament;
import ep.martialartstournament.martialartstournament.model.DivisionDTO;
import ep.martialartstournament.martialartstournament.repos.DivisionRepository;
import ep.martialartstournament.martialartstournament.repos.TournamentRepository;
import ep.martialartstournament.martialartstournament.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DivisionService {

    private final DivisionRepository divisionRepository;
    private final TournamentRepository tournamentRepository;

    public DivisionService(final DivisionRepository divisionRepository,
            final TournamentRepository tournamentRepository) {
        this.divisionRepository = divisionRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public List<DivisionDTO> findAll() {
        final List<Division> divisions = divisionRepository.findAll(Sort.by("id"));
        return divisions.stream()
                .map((division) -> mapToDTO(division, new DivisionDTO()))
                .toList();
    }

    public DivisionDTO get(final Integer id) {
        return divisionRepository.findById(id)
                .map(division -> mapToDTO(division, new DivisionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DivisionDTO divisionDTO) {
        final Division division = new Division();
        mapToEntity(divisionDTO, division);
        return divisionRepository.save(division).getId();
    }

    public void update(final Integer id, final DivisionDTO divisionDTO) {
        final Division division = divisionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(divisionDTO, division);
        divisionRepository.save(division);
    }

    public void delete(final Integer id) {
        divisionRepository.deleteById(id);
    }

    private DivisionDTO mapToDTO(final Division division, final DivisionDTO divisionDTO) {
        divisionDTO.setId(division.getId());
        divisionDTO.setName(division.getName());
        divisionDTO.setWeightClass(division.getWeightClass());
        divisionDTO.setBeltRank(division.getBeltRank());
        divisionDTO.setAgeGroup(division.getAgeGroup());
        divisionDTO.setTournament(division.getTournament() == null ? null : division.getTournament().getId());
        return divisionDTO;
    }

    private Division mapToEntity(final DivisionDTO divisionDTO, final Division division) {
        division.setName(divisionDTO.getName());
        division.setWeightClass(divisionDTO.getWeightClass());
        division.setBeltRank(divisionDTO.getBeltRank());
        division.setAgeGroup(divisionDTO.getAgeGroup());
        final Tournament tournament = divisionDTO.getTournament() == null ? null : tournamentRepository.findById(divisionDTO.getTournament())
                .orElseThrow(() -> new NotFoundException("tournament not found"));
        division.setTournament(tournament);
        return division;
    }

}
