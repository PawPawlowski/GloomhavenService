package gloomhavenService.services.model.domain.persistence;

import gloomhavenService.services.model.domain.Scenario;
import gloomhavenService.services.model.domain.ScenarioAchievement;
import gloomhavenService.services.model.domain.ScenarioTrigger;
import gloomhavenService.services.model.enumerations.ScenarioRequirementType;
import gloomhavenService.services.model.enumerations.ScenarioStatus;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public class ScenarioJpa extends BaseJpa {

    @Column(nullable = false)
    private String name;

    private String location;

    @Column(nullable = false)
    private int ref;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScenarioStatus status;

    @Column(nullable = false)
    private boolean completed;

    private String goal;

    private String reward;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScenarioRequirementType requirementType;

    @OneToOne
    @JoinColumn(referencedColumnName = "ID", name = "LINkS")
    private Scenario links;

    @OneToMany(targetEntity = ScenarioAchievement.class)
    @JoinColumn(name = "SCENARIO_ID")
    private List<ScenarioAchievement> scenarioAchievements;

    @OneToMany(targetEntity = ScenarioTrigger.class)
    @JoinColumn(name = "SCENARIO_ID")
    private List<ScenarioTrigger> scenarioTriggers;

    @OneToMany(targetEntity = ScenarioTrigger.class)
    @JoinColumn(name = "AFFECTED_SCENARIO_ID")
    private List<ScenarioTrigger> affectedByTriggers;

    public ScenarioJpa() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public ScenarioStatus getStatus() {
        return status;
    }

    public void setStatus(ScenarioStatus status) {
        this.status = status;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public ScenarioRequirementType getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(ScenarioRequirementType requirementType) {
        this.requirementType = requirementType;
    }

    public Scenario getLinks() {
        return links;
    }

    public void setLinks(Scenario links) {
        this.links = links;
    }

    public List<ScenarioAchievement> getScenarioAchievements() {
        return scenarioAchievements;
    }

    public void setScenarioAchievements(List<ScenarioAchievement> scenarioAchievements) {
        this.scenarioAchievements = scenarioAchievements;
    }

    public List<ScenarioTrigger> getScenarioTriggers() {
        return scenarioTriggers;
    }

    public void setScenarioTriggers(List<ScenarioTrigger> scenarioTriggers) {
        this.scenarioTriggers = scenarioTriggers;
    }

    public List<ScenarioTrigger> getAffectedByTriggers() {
        return affectedByTriggers;
    }

    public void setAffectedByTriggers(List<ScenarioTrigger> affectedByTriggers) {
        this.affectedByTriggers = affectedByTriggers;
    }


}
