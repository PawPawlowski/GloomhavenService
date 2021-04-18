package gloomhavenService.services.model.view;

import gloomhavenService.services.model.domain.Achievement;
import gloomhavenService.services.model.domain.ScenarioAchievement;
import gloomhavenService.services.model.enumerations.AchievementType;

public class AchievementView {
    private int number;
    private AchievementType type;
    private Boolean reqObtained;
    private Boolean fullFilled;
    private String text;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public AchievementType getType() {
        return type;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }

    public Boolean isReqObtained() {
        return reqObtained;
    }

    public void setReqObtained(Boolean reqObtained) {
        this.reqObtained = reqObtained;
    }

    public Boolean isFullFilled() {
        return fullFilled;
    }

    public void setFullFilled(Boolean fullFilled) {
        this.fullFilled = fullFilled;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private AchievementView(int number, AchievementType type, Boolean reqObtained, Boolean fullFilled, String text) {
        this.number = number;
        this.type = type;
        this.reqObtained = reqObtained;
        this.fullFilled = fullFilled;
        this.text = text;
    }

    public static AchievementView fromAchievement(ScenarioAchievement scenarioAchievement) {
        Achievement achievement = scenarioAchievement.getAchievement();
        return new AchievementView(
                achievement.getRef(),
                achievement.getType(),
                scenarioAchievement.requirementIsObtain(),
                scenarioAchievement.isSatisfied(),
                achievement.getName());
    }

    public static AchievementView fromAchievement(Achievement achievement) {
        return new AchievementView(
                achievement.getRef(),
                achievement.getType(),
                null,
                achievement.isObtained(),
                achievement.getName());
    }
}
