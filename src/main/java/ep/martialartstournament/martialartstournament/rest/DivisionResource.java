package ep.martialartstournament.martialartstournament.rest;

import ep.martialartstournament.martialartstournament.service.DivisionService;
import ep.martialartstournament.martialartstournament.model.DivisionDTO;
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
@RequestMapping(value = "/api/divisions", produces = MediaType.APPLICATION_JSON_VALUE)
public class DivisionResource {

    private final DivisionService divisionService;

    public DivisionResource(final DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @GetMapping
    public ResponseEntity<List<DivisionDTO>> getAllDivisions() {
        return ResponseEntity.ok(divisionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DivisionDTO> getDivision(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(divisionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDivision(
            @RequestBody @Valid final DivisionDTO divisionDTO) {
        return new ResponseEntity<>(divisionService.create(divisionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDivision(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final DivisionDTO divisionDTO) {
        divisionService.update(id, divisionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDivision(@PathVariable(name = "id") final Integer id) {
        divisionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
