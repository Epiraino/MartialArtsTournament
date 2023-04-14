package ep.martialartstournament.martialartstournament.rest;

import ep.martialartstournament.martialartstournament.model.BracketDTO;
import ep.martialartstournament.martialartstournament.service.BracketService;
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
@RequestMapping(value = "/api/brackets", produces = MediaType.APPLICATION_JSON_VALUE)
public class BracketResource {

    private final BracketService bracketService;

    public BracketResource(final BracketService bracketService) {
        this.bracketService = bracketService;
    }

    @GetMapping
    public ResponseEntity<List<BracketDTO>> getAllBrackets() {
        return ResponseEntity.ok(bracketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BracketDTO> getBracket(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(bracketService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBracket(@RequestBody @Valid final BracketDTO bracketDTO) {
        return new ResponseEntity<>(bracketService.create(bracketDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBracket(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final BracketDTO bracketDTO) {
        bracketService.update(id, bracketDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBracket(@PathVariable(name = "id") final Integer id) {
        bracketService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
