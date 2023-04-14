package ep.martialartstournament.martialartstournament.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AnalyticsDTO {

    private Integer id;

    @NotNull
    private Integer registeredFightersCount;

    @NotNull
    private Integer completedMatchesCount;

    private String matchStatistics;

    private Integer tournament;

}
