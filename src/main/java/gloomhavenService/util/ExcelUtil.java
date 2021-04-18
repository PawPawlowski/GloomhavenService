package gloomhavenService.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;
import java.util.Optional;

public class ExcelUtil {

    public static boolean isHeaderRow(Row row) {
        return row.getRowNum() == 0;
    }

    public static Optional<String> getValue(Row row, Map<String, Integer> indeces, String column) {
        return Optional.ofNullable(column)
                .map(indeces::get)
                .map(row::getCell)
                .map(Cell::getStringCellValue);
    }

    public static int getIntValue(Row row, Map<String, Integer> indeces, String column) {
        return Optional.ofNullable(column)
                .map(indeces::get)
                .map(row::getCell)
                .map(Cell::getNumericCellValue)
                .map(Double::intValue)
                .orElse(0);
    }
}
