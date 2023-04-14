package ep.martialartstournament.martialartstournament.rest;

import ep.martialartstournament.martialartstournament.model.LiveStreamDTO;
import ep.martialartstournament.martialartstournament.service.LiveStreamService;
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
@RequestMapping(value = "/api/liveStreams", produces = MediaType.APPLICATION_JSON_VALUE)
public class LiveStreamResource {

    private final LiveStreamService liveStreamService;

    public LiveStreamResource(final LiveStreamService liveStreamService) {
        this.liveStreamService = liveStreamService;
    }

    @GetMapping
    public ResponseEntity<List<LiveStreamDTO>> getAllLiveStreams() {
        return ResponseEntity.ok(liveStreamService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveStreamDTO> getLiveStream(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(liveStreamService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createLiveStream(
            @RequestBody @Valid final LiveStreamDTO liveStreamDTO) {
        return new ResponseEntity<>(liveStreamService.create(liveStreamDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLiveStream(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final LiveStreamDTO liveStreamDTO) {
        liveStreamService.update(id, liveStreamDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLiveStream(@PathVariable(name = "id") final Integer id) {
        liveStreamService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
