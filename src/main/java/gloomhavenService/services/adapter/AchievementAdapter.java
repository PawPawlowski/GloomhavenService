package gloomhavenService.services.adapter;

import gloomhavenService.services.adapter.api.IAchievementAdapter;
import gloomhavenService.services.model.domain.Achievement;
import gloomhavenService.services.model.view.AchievementView;
import gloomhavenService.services.repository.AchievementNumberSpecification;
import gloomhavenService.services.repository.beans.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class AchievementAdapter implements IAchievementAdapter {

    private final AchievementRepository achievementRepository;

    @Autowired
    AchievementAdapter(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public List<AchievementView> getObtainedAchievements() {
        return achievementRepository.findAll().stream()
                .filter(Achievement::isObtained)
                .map(AchievementView::fromAchievement)
                .collect(Collectors.toList());
    }

    @Override
    public List<AchievementView> getUnobtainedAchievements() {
        return achievementRepository.findAll().stream()
                .filter(a -> !a.isObtained())
                .map(AchievementView::fromAchievement)
                .collect(Collectors.toList());
    }

    @Override
    public boolean setStatus(int number, boolean obtained) {
        return findByNumber(number)
                .map(a -> {
                    a.setObtained(obtained);
                    achievementRepository.save(a);
                    return true;
                }).orElse(false);
    }

    private Optional<Achievement> findByNumber(int number) {
        return Optional.of(number)
                .map(AchievementNumberSpecification::ofNumber)
                .flatMap(achievementRepository::findOne);
    }

}
