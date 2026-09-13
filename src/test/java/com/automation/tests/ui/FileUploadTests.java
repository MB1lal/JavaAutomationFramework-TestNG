package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.FileUploadPage;
import com.automation.pages.HomePage;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code file-upload.feature}.
 */
@Test(groups = "ui")
public class FileUploadTests extends UiBaseTest {

    @Test(description = "Chosen file is uploaded and its name shown back")
    public void uploadFile() {
        FileUploadPage uploadPage = new HomePage(driver()).open().goTo("file upload");
        String file = Paths.get(System.getProperty("user.dir"),
                "src", "test", "resources", "data-files", "UploadFile.txt")
                .toAbsolutePath().toString();

        uploadPage.upload(file);

        assertThat(uploadPage.headerText()).contains("File Uploaded!");
        assertThat(uploadPage.uploadedFileName()).isEqualTo("UploadFile.txt");
    }
}
