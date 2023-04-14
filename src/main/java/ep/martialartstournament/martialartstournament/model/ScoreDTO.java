package ep.martialartstournament.martialartstournament.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScoreDTO {

    private Integer id;

    @NotNull
    private Integer fighter1Score;

    @NotNull
    private Integer fighter2Score;

    private Integer match;

    private Integer referee;

}
