package gloomhavenService.services.adapter.api;

import gloomhavenService.services.model.view.ScenarioDetailsView;
import gloomhavenService.services.model.view.ScenarioView;

import java.util.List;

public interface IScenarioAdapter {

    List<ScenarioView> getScenarios(boolean includeCompleted);

    List<ScenarioView> getBlocked(boolean includeCompleted);

    List<ScenarioView> getLocked();

    boolean unlock(int number);

    List<ScenarioView> findAll();

    ScenarioDetailsView getDetails(int number);

    boolean complete(int number);

}
