package ep.martialartstournament.martialartstournament.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DivisionDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String weightClass;

    @Size(max = 255)
    private String beltRank;

    @Size(max = 255)
    private String ageGroup;

    private Integer tournament;

}
