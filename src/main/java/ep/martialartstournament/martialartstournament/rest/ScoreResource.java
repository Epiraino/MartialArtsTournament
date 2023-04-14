package ep.martialartstournament.martialartstournament.rest;

import ep.martialartstournament.martialartstournament.model.ScoreDTO;
import ep.martialartstournament.martialartstournament.service.ScoreService;
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
@RequestMapping(value = "/api/scores", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScoreResource {

    private final ScoreService scoreService;

    public ScoreResource(final ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public ResponseEntity<List<ScoreDTO>> getAllScores() {
        return ResponseEntity.ok(scoreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreDTO> getScore(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(scoreService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createScore(@RequestBody @Valid final ScoreDTO scoreDTO) {
        return new ResponseEntity<>(scoreService.create(scoreDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateScore(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final ScoreDTO scoreDTO) {
        scoreService.update(id, scoreDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteScore(@PathVariable(name = "id") final Integer id) {
        scoreService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
