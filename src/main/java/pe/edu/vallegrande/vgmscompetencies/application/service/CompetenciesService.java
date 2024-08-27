package pe.edu.vallegrande.vgmscompetencies.application.service;

import pe.edu.vallegrande.vgmscompetencies.domain.dto.CompetenciesCreateDto;
import pe.edu.vallegrande.vgmscompetencies.domain.dto.CompetenciesIdsDto;
import pe.edu.vallegrande.vgmscompetencies.domain.dto.CompetenciesUpdateDto;
import pe.edu.vallegrande.vgmscompetencies.domain.dto.DidacticUnit;
import pe.edu.vallegrande.vgmscompetencies.domain.model.Competencies;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompetenciesService {

    Flux<Competencies> getByStatus(String status);
    Mono<Competencies> create(CompetenciesCreateDto competenciesCreateDto);
    Mono<Competencies> update(String id, CompetenciesUpdateDto competenciesUpdateDto);
    Mono<Competencies> changeStatus(String id, String status);
    Mono<Competencies> getByIds(String id);
    Mono<DidacticUnit> getById(String id);
    Flux<DidacticUnit> listActive();
    Flux<Competencies> assignCompetenciesToDidacticUnit(String didacticUnitId, CompetenciesIdsDto competenciesIdsDto);
    Flux<Competencies> getByDidacticUnitId(String didacticUnitId);
}
