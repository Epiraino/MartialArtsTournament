package ep.martialartstournament.martialartstournament.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FighterDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private Double weight;

    @NotNull
    @Size(max = 255)
    private String beltRank;

    private String contactInformation;

    private Integer user;

    private Integer division;

}
