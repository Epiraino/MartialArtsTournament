package ep.martialartstournament.martialartstournament.rest;

import ep.martialartstournament.martialartstournament.model.MatchDTO;
import ep.martialartstournament.martialartstournament.service.MatchService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/matchs", produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchResource {

    private final MatchService matchService;

    public MatchResource(final MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatchs() {
        return ResponseEntity.ok(matchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatch(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(matchService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMatch(@RequestBody @Valid final MatchDTO matchDTO) {
        return new ResponseEntity<>(matchService.create(matchDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMatch(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final MatchDTO matchDTO) {
        matchService.update(id, matchDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMatch(@PathVariable(name = "id") final Integer id) {
        matchService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
