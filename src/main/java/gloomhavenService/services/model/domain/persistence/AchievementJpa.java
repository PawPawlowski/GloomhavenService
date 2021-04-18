package gloomhavenService.services.model.domain.persistence;

import gloomhavenService.services.model.domain.ScenarioAchievement;
import gloomhavenService.services.model.enumerations.AchievementType;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public class AchievementJpa extends BaseJpa {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int ref;

    @Column(nullable = false)
    private boolean obtained;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AchievementType type;

    @OneToMany(targetEntity = ScenarioAchievement.class)
    private List<ScenarioAchievement> scenarioAchievements;

    public AchievementJpa() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public boolean isObtained() {
        return obtained;
    }

    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }

    public AchievementType getType() {
        return type;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }

    public List<ScenarioAchievement> getScenarioAchievements() {
        return scenarioAchievements;
    }

    public void setScenarioAchievements(List<ScenarioAchievement> scenarioAchievements) {
        this.scenarioAchievements = scenarioAchievements;
    }
}
