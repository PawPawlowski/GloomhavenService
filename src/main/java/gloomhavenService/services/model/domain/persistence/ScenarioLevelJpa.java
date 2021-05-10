package gloomhavenService.services.model.domain.persistence;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ScenarioLevelJpa {

    @Id
    @Column(nullable = false)
    private int scenario;

    @Column(nullable = false)
    private int monster;

    @Column(nullable = false)
    private int gold;

    @Column(nullable = false)
    private int trap;

    @Column(nullable = false)
    private int exp;

    public ScenarioLevelJpa() {
    }

    public int getScenario() {
        return scenario;
    }

    public void setScenario(int scenario) {
        this.scenario = scenario;
    }

    public int getMonster() {
        return monster;
    }

    public void setMonster(int monster) {
        this.monster = monster;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getTrap() {
        return trap;
    }

    public void setTrap(int trap) {
        this.trap = trap;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
