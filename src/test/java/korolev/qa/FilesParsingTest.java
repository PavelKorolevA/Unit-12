package korolev.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static com.codeborne.selenide.Selectors.byText;

public class FilesParsingTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void parsePdfTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/ ");
        File pdfDownload = Selenide.$(byText("PDF download")).download();
        PDF pdf = new PDF(pdfDownload);
        assertThat(pdf.author).contains("Marc Philipp");
        assertThat(pdf.numberOfPages).isEqualTo(166);
    }


    @Test
    void parseXlsTest() throws Exception {
        Selenide.open("http://romashka2008.ru/price");
        File xlsDownload = Selenide.$(".site-main__inner a[href*='prajs_ot']").download();
        XLS xls = new XLS(xlsDownload);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(11)
                .getCell(1)
                .getStringCellValue()).contains("693010, Сахалинская обл, Южно-Сахалинск г");
    }

    @Test
    void parseCsvTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("files/business-financial-data-sep-2021-quarter.csv");
             CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            List<String[]> content = reader.readAll();
            Assertions.assertThat(content.get(0)).contains(
                    "Series_reference",
                    "Period",
                    "Data_value",
                    "Suppressed",
                    "STATUS",
                    "UNITS",
                    "Magnitude",
                    "Subject",
                    "Group",
                    "Series_title_1",
                    "Series_title_2",
                    "Series_title_3",
                    "Series_title_4",
                    "Series_title_5");
        }
    }
}