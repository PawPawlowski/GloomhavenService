package gloomhavenService.datamanagement.reader;

import gloomhavenService.datamanagement.adapter.constants.ScenarioSheet;
import gloomhavenService.services.model.domain.Achievement;
import gloomhavenService.services.model.domain.Scenario;
import gloomhavenService.services.model.domain.ScenarioAchievement;
import gloomhavenService.services.model.enumerations.ScenarioAchievementRelation;
import gloomhavenService.services.repository.*;
import gloomhavenService.services.repository.beans.AchievementRepository;
import gloomhavenService.services.repository.beans.ScenarioAchievementRepository;
import gloomhavenService.services.repository.beans.ScenarioRepository;
import gloomhavenService.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Optional;

public class ScenarioAchievementReader extends BaseReader<ScenarioAchievement> {

    private final ScenarioRepository scenarioRepository;
    private final AchievementRepository achievementRepository;

    private ScenarioAchievementReader(XSSFWorkbook workbook,
                                      ScenarioAchievementRepository repository,
                                      ScenarioRepository scenarioRepository,
                                      AchievementRepository achievementRepository) {
        super(workbook, repository);
        this.scenarioRepository = scenarioRepository;
        this.achievementRepository = achievementRepository;
    }

    public static ScenarioAchievementReader init(XSSFWorkbook workbook,
                                                 ScenarioAchievementRepository repository,
                                                 ScenarioRepository scenarioRepository,
                                                 AchievementRepository achievementRepository) {
        return new ScenarioAchievementReader(workbook, repository, scenarioRepository, achievementRepository);
    }

    @Override
    protected XSSFSheet getSheet(XSSFWorkbook workbook) {
        return workbook.getSheet(ScenarioSheet.Sheet.ScenarioAchievement.NAME);
    }

    @Override
    protected Optional<ScenarioAchievement> readSingleRow(Row row) {
        Optional<ScenarioAchievementRelation> relation = ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.ScenarioAchievement.Column.RELATION)
                .map(String::toUpperCase)
                .map(ScenarioAchievementRelation::valueOf);

        Optional<Scenario> scenario = Optional.of(ExcelUtil.getIntValue(row, indeces, ScenarioSheet.Sheet.ScenarioAchievement.Column.SCENARIO))
                .filter(i -> i > 0)
                .map(ScenarioNumberSpecification::ofNumber)
                .flatMap(scenarioRepository::findOne);

        Optional<Achievement> achievement = Optional.of(ExcelUtil.getIntValue(row, indeces, ScenarioSheet.Sheet.ScenarioAchievement.Column.ACHIEVEMENT))
                .filter(i -> i > 0)
                .map(AchievementNumberSpecification::ofNumber)
                .flatMap(achievementRepository::findOne);

        if (allPresent(relation, scenario, achievement)) {
            ScenarioAchievement result = new ScenarioAchievement();
            relation.ifPresent(result::setRelation);
            scenario.ifPresent(result::setScenario);
            achievement.ifPresent(result::setAchievement);
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
