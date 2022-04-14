package korolev.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideFileTest {

    @Test
    void selenideDownloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadedfile = Selenide.$("#raw-url").download();
        try (InputStream is = new FileInputStream(downloadedfile)) {
            assertThat(new String(is.readAllBytes(), StandardCharsets.UTF_8)).contains("This repository");
        }
    }

    @Test
    void uploadSelenideTest() {
        Selenide.open("https://the-internet.herokuapp.com/upload");
        Selenide.$("input[type='file']")
//                .uploadFile(new File("/Users/dmitriituchs/IdeaProjects/qa_guru/qa_guru_11_9_files/src/test/resources/files/1.txt")); // bad practice!
                  .uploadFromClasspath("files/1.txt");
        Selenide.$("#file-submit").click();
        Selenide.$("div.example").shouldHave(Condition.text("File Uploaded!"));
        Selenide.$("#uploaded-files").shouldHave(Condition.text("1.txt"));
    }
}
