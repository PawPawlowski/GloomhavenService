package gloomhavenService.services.model.view;

import gloomhavenService.services.model.domain.ScenarioLevel;

public class ScenarioLevelView {
    private int scenario;
    private int monster;
    private int gold;
    private int trapDamage;
    private int experience;

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

    public int getTrapDamage() {
        return trapDamage;
    }

    public void setTrapDamage(int trapDamage) {
        this.trapDamage = trapDamage;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    private ScenarioLevelView(int scenario, int monster, int gold, int trapDamage, int experience) {
        this.scenario = scenario;
        this.monster = monster;
        this.gold = gold;
        this.trapDamage = trapDamage;
        this.experience = experience;
    }

    public static ScenarioLevelView fromScenarioView(ScenarioLevel scenarioLevel) {
        return new ScenarioLevelView(
                scenarioLevel.getScenario(),
                scenarioLevel.getMonster(),
                scenarioLevel.getGold(),
                scenarioLevel.getTrap(),
                scenarioLevel.getExp()
        );
    }
}
