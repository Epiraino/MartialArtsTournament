package ep.martialartstournament.martialartstournament.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BracketDTO {

    private Integer id;

    @NotNull
    private Integer roundNumber;

    private Integer match;

    private Integer division;

    private Integer winner;

}
