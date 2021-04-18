package gloomhavenService.services.adapter.api;

import gloomhavenService.services.model.view.AchievementView;

import java.util.List;

public interface IAchievementAdapter {

    List<AchievementView> getObtainedAchievements();

    List<AchievementView> getUnobtainedAchievements();

    public boolean setStatus(int number, boolean obtained);
}
