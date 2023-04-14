package ep.martialartstournament.martialartstournament.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LiveStreamDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String url;

    @NotNull
    @Size(max = 255)
    private String status;

    private Integer match;

}
