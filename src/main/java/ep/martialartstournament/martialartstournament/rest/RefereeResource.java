package ep.martialartstournament.martialartstournament.rest;

import ep.martialartstournament.martialartstournament.model.RefereeDTO;
import ep.martialartstournament.martialartstournament.service.RefereeService;
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
@RequestMapping(value = "/api/referees", produces = MediaType.APPLICATION_JSON_VALUE)
public class RefereeResource {

    private final RefereeService refereeService;

    public RefereeResource(final RefereeService refereeService) {
        this.refereeService = refereeService;
    }

    @GetMapping
    public ResponseEntity<List<RefereeDTO>> getAllReferees() {
        return ResponseEntity.ok(refereeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefereeDTO> getReferee(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(refereeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createReferee(@RequestBody @Valid final RefereeDTO refereeDTO) {
        return new ResponseEntity<>(refereeService.create(refereeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReferee(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final RefereeDTO refereeDTO) {
        refereeService.update(id, refereeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReferee(@PathVariable(name = "id") final Integer id) {
        refereeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
