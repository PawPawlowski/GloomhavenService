package gloomhavenService.datamanagement.adapter;

import gloomhavenService.datamanagement.adapter.api.IExcelAdapter;
import gloomhavenService.datamanagement.reader.AchievementReader;
import gloomhavenService.datamanagement.reader.ScenarioAchievementReader;
import gloomhavenService.datamanagement.reader.ScenarioReader;
import gloomhavenService.datamanagement.reader.TriggerReader;
import gloomhavenService.services.repository.beans.AchievementRepository;
import gloomhavenService.services.repository.beans.ScenarioAchievementRepository;
import gloomhavenService.services.repository.beans.ScenarioRepository;
import gloomhavenService.services.repository.beans.ScenarioTriggerRepository;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class ExcelAdapter implements IExcelAdapter {

    private final AchievementRepository achievementRepository;
    private final ScenarioRepository scenarioRepository;
    private final ScenarioTriggerRepository triggerRepository;
    private final ScenarioAchievementRepository scenarioAchievementRepository;

    @Autowired
    ExcelAdapter(AchievementRepository achievementRepository,
                 ScenarioRepository scenarioRepository,
                 ScenarioTriggerRepository triggerRepository,
                 ScenarioAchievementRepository scenarioAchievementRepository) {
        this.achievementRepository = achievementRepository;
        this.scenarioRepository = scenarioRepository;
        this.triggerRepository = triggerRepository;
        this.scenarioAchievementRepository = scenarioAchievementRepository;
    }

    private static String BASE_URL = "C:\\Users\\joje\\Gloomhaven\\GloomhavenService\\src\\main\\resources";
    private static String SHEET_URL = "\\scenarios\\scenarios.xlsx";

    @Transactional
    public void readScenariosFromFile() {
        XSSFWorkbook wb;
        try {
            FileInputStream is = new FileInputStream(BASE_URL + SHEET_URL);
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            System.out.printf("Fejl ved at læse fil (%s) - %s", SHEET_URL, e.getMessage());
            System.out.println();
            return;
        }

        ScenarioReader scenarioReader = ScenarioReader.init(wb, scenarioRepository);
        scenarioReader.read();
        scenarioReader.persist();

        AchievementReader achievementReader = AchievementReader.init(wb, achievementRepository);
        achievementReader.read();
        achievementReader.persist();

        TriggerReader triggerReader = TriggerReader.init(wb, triggerRepository, scenarioRepository);
        triggerReader.read();
        triggerReader.persist();

        ScenarioAchievementReader scenarioAchievementReader = ScenarioAchievementReader.init(wb, scenarioAchievementRepository, scenarioRepository, achievementRepository);
        scenarioAchievementReader.read();
        scenarioAchievementReader.persist();
    }


}
