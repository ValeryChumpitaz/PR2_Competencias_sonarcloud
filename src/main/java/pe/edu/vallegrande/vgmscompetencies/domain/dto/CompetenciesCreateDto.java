package pe.edu.vallegrande.vgmscompetencies.domain.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CompetenciesCreateDto {

    @Id
    private String competencyId;
    private String name;
    private String description;
    private String didacticUnitId;
    private String status;

}
