package gloomhavenService.datamanagement.reader;

import gloomhavenService.services.repository.BaseRepository;
import gloomhavenService.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.MappedSuperclass;
import java.util.*;

@MappedSuperclass
public abstract class BaseReader<T> {

    private final XSSFWorkbook workbook;
    private final BaseRepository<T> repository;

    private boolean hasRead = false;
    private List<T> result;
    protected Map<String, Integer> indeces;

    protected abstract Optional<T> readSingleRow(Row row);

    protected abstract XSSFSheet getSheet(XSSFWorkbook workbook);

    protected BaseReader(XSSFWorkbook workbook, BaseRepository<T> repository) {
        this.workbook = workbook;
        this.repository = repository;
        this.indeces = readHeaderRow(getSheet(workbook));
    }

    public void persist() {
        if (hasRead) {
            repository.saveAll(result);
        }
    }

    public void read() {
        XSSFSheet sheet = getSheet(workbook);

        List<T> result = new ArrayList<>();
        sheet.forEach(r -> {
            if (!ExcelUtil.isHeaderRow(r)) {
                readSingleRow(r).ifPresent(result::add);
            }
        });

        this.result = postReadUpdater(result, getSheet(workbook));
        this.hasRead = true;
    }

    protected Map<String, Integer> readHeaderRow(XSSFSheet sheet) {
        Map<String, Integer> indeces = new HashMap<>();
        XSSFRow row = sheet.getRow(0);
        row.forEach(c -> indeces.put(c.getStringCellValue(), c.getColumnIndex()));
        return indeces;
    }

    protected boolean allPresent(Optional... args) {
        return Arrays.stream(args).allMatch(Optional::isPresent);
    }

    protected List<T> postReadUpdater(List<T> result, XSSFSheet sheet) {
        return result;
    }

}
