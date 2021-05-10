package gloomhavenService.services.controller;

import gloomhavenService.services.adapter.api.IScenarioLevelAdapter;
import gloomhavenService.services.model.view.ScenarioLevelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ComponentScan(basePackages = {"gloomhavenService"})
@RestController
@CrossOrigin
public class ScenarioLevelController {

    private final IScenarioLevelAdapter adapter;

    @Autowired
    ScenarioLevelController(IScenarioLevelAdapter adapter) {
        this.adapter = adapter;
    }

    @GetMapping("/misc/scenariolevels")
    List<ScenarioLevelView> scenariolevels() {
        return adapter.getScenarioLevels();
    }
}
