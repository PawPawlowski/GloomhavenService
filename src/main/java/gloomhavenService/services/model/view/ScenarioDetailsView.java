package gloomhavenService.services.model.view;

import gloomhavenService.services.model.domain.Scenario;
import gloomhavenService.services.model.domain.ScenarioAchievement;
import gloomhavenService.services.model.domain.ScenarioTrigger;
import gloomhavenService.services.model.enumerations.ScenarioAchievementRelation;
import gloomhavenService.services.model.enumerations.ScenarioTriggerAction;

import java.util.List;
import java.util.stream.Collectors;

public class ScenarioDetailsView extends ScenarioView {
    private String objective;
    private String rewards;
    private List<ScenarioView> unlock;
    private List<AchievementView> requirement;
    private List<AchievementView> achievement;

    protected ScenarioDetailsView(int number, String name, String location, boolean completed, String objective, String rewards) {
        super(number, name, location, completed);
        this.objective = objective;
        this.rewards = rewards;
    }

    public List<ScenarioView> getUnlock() {
        return unlock;
    }

    public void setUnlock(List<ScenarioView> unlock) {
        this.unlock = unlock;
    }

    public List<AchievementView> getRequirement() {
        return requirement;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public void setRequirement(List<AchievementView> requirement) {
        this.requirement = requirement;
    }

    public List<AchievementView> getAchievement() {
        return achievement;
    }

    public void setAchievement(List<AchievementView> achievement) {
        this.achievement = achievement;
    }

    public static ScenarioDetailsView fromScenario(Scenario scenario) {
        ScenarioDetailsView result = new ScenarioDetailsView(
                scenario.getRef(),
                scenario.getName(),
                scenario.getLocation(),
                scenario.getCompleted(),
                scenario.getGoal(),
                scenario.getReward());

        List<ScenarioView> unlocks = scenario.getScenarioTriggers().stream()
                .filter(t -> t.getAction() == ScenarioTriggerAction.UNLOCK)
                .map(ScenarioTrigger::getAffectedScenario)
                .map(ScenarioView::fromScenario)
                .collect(Collectors.toList());
        result.setUnlock(unlocks);

        List<AchievementView> achieves = scenario.getScenarioAchievements().stream()
                .filter(a -> a.getRelation() == ScenarioAchievementRelation.OBTAIN)
                .map(ScenarioAchievement::getAchievement)
                .map(AchievementView::fromAchievement)
                .collect(Collectors.toList());
        result.setAchievement(achieves);

        List<AchievementView> requires = scenario.getScenarioAchievements().stream()
                .filter(ScenarioAchievement::isRequirement)
                .map(AchievementView::fromAchievement)
                .collect(Collectors.toList());
        result.setRequirement(requires);

        return result;
    }
}
