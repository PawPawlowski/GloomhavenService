package gloomhavenService.services.model.enumerations;

import gloomhavenService.services.model.domain.Scenario;
import gloomhavenService.services.model.domain.ScenarioAchievement;

import java.util.function.Predicate;

public enum ScenarioRequirementType {

    ALL(allRequirementsChecker()),
    ANY(anyRequirementsChecker());

    private final Predicate<Scenario> satisfyChecker;

    ScenarioRequirementType(Predicate<Scenario> satisfyChecker) {
        this.satisfyChecker = satisfyChecker;
    }

    public Predicate<Scenario> getChecker() {
        return satisfyChecker;
    }

    private static Predicate<Scenario> allRequirementsChecker() {
        return s -> s.getScenarioAchievements().stream()
                .filter(ScenarioAchievement::isRequirement)
                .allMatch(ScenarioAchievement::isSatisfied);
    }

    private static Predicate<Scenario> anyRequirementsChecker() {
        return s -> s.getScenarioAchievements().stream()
                .filter(ScenarioAchievement::isRequirement)
                .anyMatch(ScenarioAchievement::isSatisfied);
    }

}
