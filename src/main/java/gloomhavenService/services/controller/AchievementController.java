package gloomhavenService.services.controller;

import gloomhavenService.services.adapter.api.IAchievementAdapter;
import gloomhavenService.services.model.view.AchievementView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ComponentScan(basePackages = {"gloomhavenService"})
@RestController
@CrossOrigin
public class AchievementController {

    private final IAchievementAdapter achievementAdapter;

    @Autowired
    AchievementController(IAchievementAdapter achievementAdapter) {
        this.achievementAdapter = achievementAdapter;
    }

    @GetMapping("/achievement/obtained")
    List<AchievementView> achievements() {
        return achievementAdapter.getObtainedAchievements();
    }

    @GetMapping("/achievement/unobtained")
    List<AchievementView> nonAchievements() {
        return achievementAdapter.getUnobtainedAchievements();
    }

    @PutMapping("/achievement/{id}")
    public boolean setStatus(@PathVariable int id, @RequestParam(required = false, defaultValue = "true") boolean obtain) {
        return achievementAdapter.setStatus(id, obtain);
    }
}
