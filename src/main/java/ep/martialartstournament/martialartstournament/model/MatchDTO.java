package ep.martialartstournament.martialartstournament.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MatchDTO {

    private Integer id;

    @NotNull
    private OffsetDateTime time;

    @NotNull
    @Size(max = 255)
    private String location;

    @NotNull
    @Size(max = 255)
    private String status;

    private Integer fighter1Id;

    private Integer fighter2Id;

    private Integer referee;

    private Integer division;

}
