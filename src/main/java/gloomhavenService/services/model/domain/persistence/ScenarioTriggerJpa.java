package gloomhavenService.services.model.domain.persistence;

import gloomhavenService.services.model.enumerations.ScenarioTriggerAction;
import gloomhavenService.services.model.domain.Scenario;

import javax.persistence.*;

@MappedSuperclass
public class ScenarioTriggerJpa extends BaseJpa {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScenarioTriggerAction action;

    @ManyToOne
    private Scenario scenario;

    @ManyToOne
    @JoinColumn(name = "AFFECTED_SCENARIO_ID")
    private Scenario affectedScenario;

    public ScenarioTriggerJpa() {
    }

    public ScenarioTriggerAction getAction() {
        return action;
    }

    public void setAction(ScenarioTriggerAction action) {
        this.action = action;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getAffectedScenario() {
        return affectedScenario;
    }

    public void setAffectedScenario(Scenario affectedScenario) {
        this.affectedScenario = affectedScenario;
    }
}
