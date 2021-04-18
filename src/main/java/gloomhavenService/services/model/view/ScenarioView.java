package gloomhavenService.services.model.view;

import gloomhavenService.services.model.domain.Scenario;

import java.io.Serializable;

public class ScenarioView implements Serializable {
    private final int number;
    private final String name;
    private final String location;
    private final boolean completed;

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public boolean isCompleted() {
        return completed;
    }

    protected ScenarioView(int number, String name, String location, boolean completed) {
        this.number = number;
        this.name = name;
        this.location = location;
        this.completed = completed;
    }

    public static ScenarioView fromScenario(Scenario scenario) {
        return new ScenarioView(scenario.getRef(), scenario.getName(), scenario.getLocation(), scenario.getCompleted());
    }
}
