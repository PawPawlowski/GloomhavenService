package gloomhavenService.services.model.domain.persistence;

import gloomhavenService.services.model.enumerations.ScenarioAchievementRelation;
import gloomhavenService.services.model.domain.Achievement;
import gloomhavenService.services.model.domain.Scenario;

import javax.persistence.*;

@MappedSuperclass
@Table(name = "SCENARIO_ACHIEVEMENT")
public class ScenarioAchievementJpa extends BaseJpa {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScenarioAchievementRelation relation;

    @ManyToOne
    @JoinColumn(name = "SCENARIO_ID")
    private Scenario scenario;

    @ManyToOne
    @JoinColumn(name = "ACHIEVEMENT_ID")
    private Achievement achievement;

    public ScenarioAchievementJpa() {
    }

    public ScenarioAchievementRelation getRelation() {
        return relation;
    }

    public void setRelation(ScenarioAchievementRelation relation) {
        this.relation = relation;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
}
