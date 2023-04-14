package ep.martialartstournament.martialartstournament.rest;

import ep.martialartstournament.martialartstournament.model.FighterDTO;
import ep.martialartstournament.martialartstournament.service.FighterService;
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
@RequestMapping(value = "/api/fighters", produces = MediaType.APPLICATION_JSON_VALUE)
public class FighterResource {

    private final FighterService fighterService;

    public FighterResource(final FighterService fighterService) {
        this.fighterService = fighterService;
    }

    @GetMapping
    public ResponseEntity<List<FighterDTO>> getAllFighters() {
        return ResponseEntity.ok(fighterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FighterDTO> getFighter(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(fighterService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createFighter(@RequestBody @Valid final FighterDTO fighterDTO) {
        return new ResponseEntity<>(fighterService.create(fighterDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFighter(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final FighterDTO fighterDTO) {
        fighterService.update(id, fighterDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFighter(@PathVariable(name = "id") final Integer id) {
        fighterService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
