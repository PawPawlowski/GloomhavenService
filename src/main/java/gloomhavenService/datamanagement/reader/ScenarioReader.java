package gloomhavenService.datamanagement.reader;

import gloomhavenService.datamanagement.adapter.constants.ScenarioSheet;
import gloomhavenService.services.model.domain.Scenario;
import gloomhavenService.services.model.enumerations.ScenarioRequirementType;
import gloomhavenService.services.model.enumerations.ScenarioStatus;
import gloomhavenService.services.repository.beans.ScenarioRepository;
import gloomhavenService.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ScenarioReader extends BaseReader<Scenario> {

    private ScenarioReader(XSSFWorkbook workbook, ScenarioRepository repository) {
        super(workbook, repository);
    }

    @Override
    protected XSSFSheet getSheet(XSSFWorkbook workbook) {
        return workbook.getSheet(ScenarioSheet.Sheet.Scenario.NAME);
    }

    public static ScenarioReader init(XSSFWorkbook workbook, ScenarioRepository repository) {
        return new ScenarioReader(workbook, repository);
    }

    @Override
    protected List<Scenario> postReadUpdater(List<Scenario> scenarios, XSSFSheet sheet) {
        Map<Integer, Integer> links = getLinks(sheet);
        links.forEach((from, to) -> findByNumber(scenarios, from).ifPresent(f ->
                findByNumber(scenarios, to).ifPresent(f::setLinks)
        ));
        return scenarios;
    }

    private Optional<Scenario> findByNumber(List<Scenario> scenarios, int number) {
        return scenarios.stream().filter(s -> s.getRef() == number).findFirst();
    }

    @Override
    protected Optional<Scenario> readSingleRow(Row row) {
        Scenario scenario = new Scenario();
        int number = ExcelUtil.getIntValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.NUMBER);
        scenario.setRef(number);
        ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.NAME)
                .ifPresent(scenario::setName);
        ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.LOCATION)
                .ifPresent(scenario::setLocation);
        ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.GOAL)
                .ifPresent(scenario::setGoal);
        ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.REWARD)
                .ifPresent(scenario::setReward);
        ScenarioStatus status = ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.STATUS)
                .map(String::toUpperCase)
                .map(ScenarioStatus::valueOf)
                .orElse(ScenarioStatus.LOCKED);
        scenario.setStatus(status);
        ScenarioRequirementType requirementType = ExcelUtil.getValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.REQUIREMENT_TYPE)
                .map(String::toUpperCase)
                .map(ScenarioRequirementType::valueOf)
                .orElse(ScenarioRequirementType.ALL);
        scenario.setRequirementType(requirementType);
        scenario.setCompleted(false);

        return Optional.of(scenario);
    }

    private Map<Integer, Integer> getLinks(XSSFSheet sheet) {
        Map<Integer, Integer> linksMap = new HashMap<>();

        sheet.forEach(r -> {
            if (!ExcelUtil.isHeaderRow(r)) {
                Optional<Pair<Integer, Integer>> rowLinks = getLinksForRow(r);
                rowLinks.map(p -> linksMap.put(p.getFirst(), p.getSecond()));
            }
        });

        return linksMap;
    }

    private Optional<Pair<Integer, Integer>> getLinksForRow(Row row) {
        int scenario = ExcelUtil.getIntValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.NUMBER);
        int links = ExcelUtil.getIntValue(row, indeces, ScenarioSheet.Sheet.Scenario.Column.LINKS);
        return scenario > 0 && links > 0 ? Optional.of(Pair.of(scenario, links)) : Optional.empty();
    }
}
