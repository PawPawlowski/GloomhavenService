package gloomhavenService.services.adapter;

import gloomhavenService.services.adapter.api.IScenarioLevelAdapter;
import gloomhavenService.services.model.view.ScenarioLevelView;
import gloomhavenService.services.repository.beans.ScenarioLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ScenarioLevelAdapter implements IScenarioLevelAdapter {

    private final ScenarioLevelRepository repository;

    @Autowired
    ScenarioLevelAdapter(ScenarioLevelRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ScenarioLevelView> getScenarioLevels() {
        return repository.findAll().stream().map(ScenarioLevelView::fromScenarioView).collect(Collectors.toList());
    }
}
