package gloomhavenService.services.adapter;

import gloomhavenService.services.adapter.api.IScenarioAdapter;
import gloomhavenService.services.model.domain.Achievement;
import gloomhavenService.services.model.domain.ScenarioAchievement;
import gloomhavenService.services.model.enumerations.ScenarioAchievementRelation;
import gloomhavenService.services.model.enumerations.ScenarioStatus;
import gloomhavenService.services.model.enumerations.ScenarioTriggerAction;
import gloomhavenService.services.model.view.ScenarioView;
import gloomhavenService.services.repository.beans.AchievementRepository;
import gloomhavenService.services.repository.ScenarioNumberSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import gloomhavenService.services.model.domain.Scenario;
import gloomhavenService.services.model.view.ScenarioDetailsView;
import gloomhavenService.services.repository.beans.ScenarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class ScenarioAdapter implements IScenarioAdapter {

    private final ScenarioRepository scenarioRepository;
    private final AchievementRepository achievementRepository;

    @Autowired
    ScenarioAdapter(ScenarioRepository scenarioRepository, AchievementRepository achievementRepository) {
        this.scenarioRepository = scenarioRepository;
        this.achievementRepository = achievementRepository;
    }

    @Override
    public List<ScenarioView> getScenarios(boolean includeCompleted) {
        return toView(findWithStatus(ScenarioStatus.UNLOCKED)
                .filter(s -> includeCompleted || !s.getCompleted())
                .filter(Scenario::requirementsSatisfied));
    }

    @Override
    public List<ScenarioView> getBlocked(boolean includeCompleted) {
        return toView(findWithStatus(ScenarioStatus.UNLOCKED)
                .filter(s -> includeCompleted || !s.getCompleted())
                .filter(s -> !s.requirementsSatisfied()));
    }

    @Override
    public List<ScenarioView> getLocked() {
        return toView(findWithStatus(ScenarioStatus.LOCKED));
    }

    @Override
    public boolean unlock(int number) {
        return findByNumber(number).map(s -> {
            boolean unlocked = s.unlock();
            scenarioRepository.save(s);
            return unlocked;
        }).orElse(false);
    }

    private List<ScenarioView> toView(Stream<Scenario> stream) {
        return stream.map(ScenarioView::fromScenario)
                .collect(Collectors.toList());
    }

    private Stream<Scenario> findWithStatus(ScenarioStatus status) {
        return scenarioRepository.findAll().stream()
                .filter(s -> s.getStatus() == status);
    }

    @Override
    public List<ScenarioView> findAll() {
        List<Scenario> all = scenarioRepository.findAll();
        return toView(all.stream());
    }

    @Override
    public ScenarioDetailsView getDetails(int number) {
        return Optional.of(number)
                .map(ScenarioNumberSpecification::ofNumber)
                .flatMap(scenarioRepository::findOne)
                .map(ScenarioDetailsView::fromScenario)
                .orElse(null);
    }

    private Optional<Scenario> findByNumber(int number) {
        return Optional.of(number)
                .map(ScenarioNumberSpecification::ofNumber)
                .flatMap(scenarioRepository::findOne);
    }

    @Override
    public boolean complete(int number) {
        Optional<Scenario> scenario = findByNumber(number);

        boolean isOpen = scenario.map(Scenario::isOpen).orElse(false);
        if (!isOpen) {
            return false;
        }

        scenario.ifPresent(s -> {
            s.setCompleted(true);
            scenarioRepository.save(s);
        });

        unlockScenarios(scenario);
        blockScenarios(scenario);
        obtainAchievements(scenario);
        removeAchievements(scenario);

        return true;
    }

    private void obtainAchievements(Optional<Scenario> scenario) {
        updateAchievements(scenario, ScenarioAchievementRelation.OBTAIN, (a) -> a.setObtained(true));
    }

    private void removeAchievements(Optional<Scenario> scenario) {
        updateAchievements(scenario, ScenarioAchievementRelation.REMOVE, (a) -> a.setObtained(false));
    }

    private void updateAchievements(Optional<Scenario> scenario, ScenarioAchievementRelation rel, Consumer<Achievement> updater) {
        scenario.map(Scenario::getScenarioAchievements)
                .orElseGet(ArrayList::new)
                .stream()
                .filter(a -> a.getRelation() == rel)
                .map(ScenarioAchievement::getAchievement)
                .forEach(a -> {
                    updater.accept(a);
                    achievementRepository.save(a);
                });
    }

    private void unlockScenarios(Optional<Scenario> scenario) {
        updateScenarios(scenario, ScenarioTriggerAction.UNLOCK, Scenario::unlock);
    }

    private void blockScenarios(Optional<Scenario> scenario) {
        updateScenarios(scenario, ScenarioTriggerAction.BLOCK, Scenario::block);
    }

    private void updateScenarios(
            Optional<Scenario> scenario,
            ScenarioTriggerAction action,
            Consumer<Scenario> updater) {
        scenario.map(s -> s.getScenariosFromTriggers(action))
                .orElseGet(Stream::empty)
                .forEach(s -> {
                    updater.accept(s);
                    scenarioRepository.save(s);
                });
    }
}
