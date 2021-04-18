package gloomhavenService.datamanagement.controller;

import gloomhavenService.datamanagement.adapter.api.IExcelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(basePackages = {"gloomhavenService"})
@RestController
@CrossOrigin
public class ExcelController {

    private final IExcelAdapter excelAdapter;

    @Autowired
    ExcelController(IExcelAdapter excelAdapter) {
        this.excelAdapter = excelAdapter;
    }

    @GetMapping("/data/readScenarios")
    void reaScenarios() {
        excelAdapter.readScenariosFromFile();
    }

}
