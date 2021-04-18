package gloomhavenService.services.model.domain;

import gloomhavenService.services.model.domain.persistence.ScenarioAchievementJpa;
import gloomhavenService.services.model.enumerations.ScenarioAchievementRelation;

import javax.persistence.Entity;
import java.util.Arrays;

@Entity
public class ScenarioAchievement extends ScenarioAchievementJpa {

    public Boolean isSatisfied() {
        if (!isRequirement()) {
            return null;
        }
        boolean obtained = this.getAchievement().isObtained();
        return (this.getRelation() == ScenarioAchievementRelation.REQUIRE && obtained)
                || (this.getRelation() == ScenarioAchievementRelation.REQUIRE_NOT && !obtained);
    }

    public boolean isRequirement() {
        return Arrays.asList(ScenarioAchievementRelation.REQUIRE, ScenarioAchievementRelation.REQUIRE_NOT)
                .contains(this.getRelation());
    }

    public Boolean requirementIsObtain() {
        return !isRequirement() ? null : getRelation() == ScenarioAchievementRelation.REQUIRE;
    }
}
