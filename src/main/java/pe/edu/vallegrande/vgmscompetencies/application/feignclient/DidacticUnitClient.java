package pe.edu.vallegrande.vgmscompetencies.application.feignclient;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.vallegrande.vgmscompetencies.domain.dto.DidacticUnit;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(name = "vg-ms-didactic-unit", url = "${client.vg-ms-didactic-unit.url}", configuration = FeignConfig.class)
public interface DidacticUnitClient {

    @GetMapping("/list/active")
    Flux<DidacticUnit> listActive();

    @GetMapping("/{id}")
    Mono<DidacticUnit> getDidacticUnitById(@PathVariable("id") String id);

}
