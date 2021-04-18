package gloomhavenService.datamanagement.reader;

import gloomhavenService.datamanagement.adapter.constants.ScenarioSheet;
import gloomhavenService.services.model.domain.Scenario;
import gloomhavenService.services.model.domain.ScenarioTrigger;
import gloomhavenService.services.model.enumerations.ScenarioTriggerAction;
import gloomhavenService.services.repository.ScenarioNumberSpecification;
import gloomhavenService.services.repository.beans.ScenarioRepository;
import gloomhavenService.services.repository.beans.ScenarioTriggerRepository;
import gloomhavenService.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Optional;

public class TriggerReader extends BaseReader<ScenarioTrigger> {

    private final ScenarioRepository scenarioRepository;

    private TriggerReader(XSSFWorkbook workbook, ScenarioTriggerRepository repository, ScenarioRepository scenarioRepository) {
        super(workbook, repository);
        this.scenarioRepository = scenarioRepository;
    }

    public static TriggerReader init(XSSFWorkbook workbook, ScenarioTriggerRepository repository, ScenarioRepository scenarioRepository) {
        return new TriggerReader(workbook, repository, scenarioRepository);
    }

    @Override
    protected XSSFSheet getSheet(XSSFWorkbook workbook) {
        return workbook.getSheet(ScenarioSheet.Sheet.Trigger.NAME);
    }

    @Override
    protected Optional<ScenarioTrigger> readSingleRow(Row row) {
        ScenarioTriggerAction action = ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Trigger.Column.ACTION)
                .map(String::toUpperCase)
                .map(ScenarioTriggerAction::valueOf)
                .orElse(ScenarioTriggerAction.UNLOCK);

        Optional<Scenario> from = Optional.of(ExcelUtil.getIntValue(row, indeces, ScenarioSheet.Sheet.Trigger.Column.FROM))
                .filter(i -> i > 0)
                .map(ScenarioNumberSpecification::ofNumber)
                .flatMap(scenarioRepository::findOne);

        Optional<Scenario> to = Optional.of(ExcelUtil.getIntValue(row, indeces, ScenarioSheet.Sheet.Trigger.Column.TO))
                .filter(i -> i > 0)
                .map(ScenarioNumberSpecification::ofNumber)
                .flatMap(scenarioRepository::findOne);

        if (allPresent(from, to)) {
            ScenarioTrigger result = new ScenarioTrigger();
            result.setAction(action);
            from.ifPresent(result::setScenario);
            to.ifPresent(result::setAffectedScenario);
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
