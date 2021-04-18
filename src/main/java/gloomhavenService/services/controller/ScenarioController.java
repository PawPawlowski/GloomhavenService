package gloomhavenService.services.controller;

import gloomhavenService.services.adapter.api.IScenarioAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import gloomhavenService.services.model.view.ScenarioDetailsView;
import gloomhavenService.services.model.view.ScenarioView;

import java.util.List;

@ComponentScan(basePackages={"gloomhavenService"})
@RestController
@CrossOrigin
public class ScenarioController {

    private final IScenarioAdapter scenarioAdapter;

    @Autowired
    ScenarioController(IScenarioAdapter scenarioAdapter) {
        this.scenarioAdapter = scenarioAdapter;
    }

    @GetMapping("/scenario/available")
    List<ScenarioView> scenarios(@RequestParam(required = false, defaultValue = "true") boolean includeCompleted) {
        return scenarioAdapter.getScenarios(includeCompleted);
    }

    @GetMapping("/scenario/blocked")
    List<ScenarioView> blockedScenarios(@RequestParam(required = false, defaultValue = "true") boolean includeCompleted) {
        return scenarioAdapter.getBlocked(includeCompleted);
    }

    @GetMapping("/scenario/locked")
    List<ScenarioView> lockedScenarios() {
        return scenarioAdapter.getLocked();
    }

    @PutMapping("/scenario/unlock/{id}")
    boolean unlockScenario(@PathVariable int id) {
        return scenarioAdapter.unlock(id);
    }

    @GetMapping("/scenario/all")
    List<ScenarioView> all() {
        return scenarioAdapter.findAll();
    }

    @GetMapping("/scenario/details/{id}")
    ScenarioDetailsView details(@PathVariable int id) {
        return scenarioAdapter.getDetails(id);
    }

    @PutMapping("/scenario/complete/{id}")
    boolean complete(@PathVariable int id) {
        return scenarioAdapter.complete(id);
    }

}
