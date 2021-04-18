package gloomhavenService.datamanagement.reader;

import gloomhavenService.datamanagement.adapter.constants.ScenarioSheet;
import gloomhavenService.services.model.domain.Achievement;
import gloomhavenService.services.model.enumerations.AchievementType;
import gloomhavenService.services.repository.beans.AchievementRepository;
import gloomhavenService.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Optional;

public class AchievementReader extends BaseReader<Achievement> {

    private AchievementReader(XSSFWorkbook workbook, AchievementRepository repository) {
        super(workbook, repository);
    }

    @Override
    protected XSSFSheet getSheet(XSSFWorkbook workbook) {
        return workbook.getSheet(ScenarioSheet.Sheet.Achievement.NAME);
    }

    public static AchievementReader init(XSSFWorkbook workbook, AchievementRepository repository) {
        return new AchievementReader(workbook, repository);
    }

    @Override
    protected Optional<Achievement> readSingleRow(Row row) {
        Achievement achievement = new Achievement();
        int number = ExcelUtil.getIntValue(row, indeces, ScenarioSheet.Sheet.Achievement.Column.NUMBER);
        achievement.setRef(number);
        ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Achievement.Column.NAME)
                .ifPresent(achievement::setName);
        ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Achievement.Column.TYPE)
                .map(String::toUpperCase)
                .map(AchievementType::valueOf)
                .ifPresent(achievement::setType);
        achievement.setObtained(false);

        return Optional.of(achievement);
    }
}
