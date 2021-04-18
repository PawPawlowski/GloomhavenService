package gloomhavenService.services.model.domain;

import gloomhavenService.services.model.enumerations.ScenarioStatus;
import gloomhavenService.services.model.enumerations.ScenarioTriggerAction;
import gloomhavenService.services.model.domain.persistence.ScenarioJpa;

import javax.persistence.Entity;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Entity
public class Scenario extends ScenarioJpa {

    public boolean unlock() {
        if (this.getStatus() != ScenarioStatus.LOCKED) {
            return false;
        }
        this.setStatus(ScenarioStatus.UNLOCKED);
        return true;
    }

    public boolean block() {
        if (this.getStatus() == ScenarioStatus.BLOCKED) {
            return false;
        }
        this.setStatus(ScenarioStatus.BLOCKED);
        return true;
    }

    public Stream<Scenario> getScenariosFromTriggers(ScenarioTriggerAction action) {
        return this.getScenarioTriggers()
                .stream()
                .filter(t -> t.getAction() == action)
                .map(ScenarioTrigger::getAffectedScenario);
    }

    public boolean isOpen() {
        return this.getStatus() == ScenarioStatus.UNLOCKED;
    }

    public boolean requirementsSatisfied() {
        Predicate<Scenario> predicate = this.getRequirementType().getChecker();
        return predicate.test(this);
    }

}
