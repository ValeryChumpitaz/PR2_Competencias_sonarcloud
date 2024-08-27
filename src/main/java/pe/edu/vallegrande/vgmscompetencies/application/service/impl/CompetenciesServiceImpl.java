package pe.edu.vallegrande.vgmscompetencies.application.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmscompetencies.application.feignclient.DidacticUnitClient;
import pe.edu.vallegrande.vgmscompetencies.application.service.CompetenciesService;
import pe.edu.vallegrande.vgmscompetencies.domain.dto.CompetenciesCreateDto;
import pe.edu.vallegrande.vgmscompetencies.domain.dto.CompetenciesIdsDto;
import pe.edu.vallegrande.vgmscompetencies.domain.dto.CompetenciesUpdateDto;
import pe.edu.vallegrande.vgmscompetencies.domain.dto.DidacticUnit;
import pe.edu.vallegrande.vgmscompetencies.domain.model.Competencies;
import pe.edu.vallegrande.vgmscompetencies.domain.repository.CompetenciesRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompetenciesServiceImpl implements CompetenciesService {

    private final CompetenciesRepository competenciesRepository;
    private final DidacticUnitClient didacticUnitClient;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CompetenciesServiceImpl(CompetenciesRepository competenciesRepository, DidacticUnitClient didacticUnitClient) {
        this.competenciesRepository = competenciesRepository;
        this.didacticUnitClient = didacticUnitClient;
    }

    @Override
    public Flux<Competencies> getByStatus(String status) {
        return competenciesRepository.findByStatus(status);
    }

    @Override
    public Mono<Competencies> create(CompetenciesCreateDto competenciesCreateDto) {
        competenciesCreateDto.setStatus("A");
        Competencies competencies = modelMapper.map(competenciesCreateDto, Competencies.class);
        return competenciesRepository.save(competencies);
    }

    @Override
    public Mono<Competencies> update(String id, CompetenciesUpdateDto competenciesUpdateDto) {
        return competenciesRepository.findByCompetencyIdAndStatus(id, "A")
                .next()
                .flatMap(sp -> {
                    modelMapper.map(competenciesUpdateDto, sp);
                    sp.setCompetencyId(id); // Ensure the ID is not changed
                    return competenciesRepository.save(sp);
                });
    }

    @Override
    public Mono<Competencies> changeStatus(String id, String status) {
        return competenciesRepository.findById(id)
                .flatMap(sp -> {
                    sp.setStatus(status);
                    return competenciesRepository.save(sp);
                });
    }

    @Override
    public Mono<Competencies> getByIds(String id) {
        return competenciesRepository.findById(id);
    }

    @Override
    public Mono<DidacticUnit> getById(String id) {
        return didacticUnitClient.getDidacticUnitById(id);
    }

    @Override
    public Flux<DidacticUnit> listActive() {
        return didacticUnitClient.listActive();
    }

    @Override
    public Flux<Competencies> assignCompetenciesToDidacticUnit(String didacticUnitId, CompetenciesIdsDto competenciesIdsDto) {
        return Flux.fromIterable(competenciesIdsDto.getCompetenciesIds())
                .flatMap(competencyId -> competenciesRepository.findById(competencyId)
                        .flatMap(competency -> {
                            competency.setDidacticUnitId(didacticUnitId);
                            competency.setStatus("A");
                            return competenciesRepository.save(competency);
                        }));
    }

    @Override
    public Flux<Competencies> getByDidacticUnitId(String didacticUnitId) {
        return competenciesRepository.findByDidacticUnitIdAndStatus(didacticUnitId, "A");
    }
}
