package korolev.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class FilesParsingTest {

    @Test
    void parseZipTest() throws Exception {
        File file = new File("src/test/resources/files/Unit12");
        ZipFile zipFile = new ZipFile(file.getAbsolutePath());
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            switch (entry.getName()) {
                case "Machine_readable_file_bdc_sf_2021_q4.csv":
                    CSVReader reader = new CSVReader(new InputStreamReader(zipFile.getInputStream(entry)));
                {
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(0)).contains(
                            "Series_reference",
                            "Period",
                            "Data_value",
                            "Suppressed",
                            "STATUS",
                            "UNITS",
                            "Magnitude",
                            "Subject",
                            "Group");
                }
                break;

                case "prajs_ot_1204.xls": {
                    InputStream is = zipFile.getInputStream(entry);
                    XLS xls = new XLS(is);
                    assertThat(xls.excel
                            .getSheetAt(0)
                            .getRow(11)
                            .getCell(1)
                            .getStringCellValue()).contains("693010, Сахалинская обл, Южно-Сахалинск г");
                    break;
                }
                case "52c8e8114c11cfa9e6e668ee652dbf2b.pdf": {
                    InputStream is = zipFile.getInputStream(entry);
                    PDF pdf = new PDF(is);
                    assertThat(pdf.text).contains("Пример PDF файла");
                    break;
                }
            }
        }
    }
}