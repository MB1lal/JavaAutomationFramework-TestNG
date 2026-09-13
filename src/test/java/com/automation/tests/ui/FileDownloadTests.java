package com.automation.tests.ui;

import com.automation.base.UiBaseTest;
import com.automation.pages.FileDownloadPage;
import com.automation.pages.HomePage;
import com.automation.utils.FileHelper;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same intent as the original {@code file-download.feature} (which was
 * {@code @ignore}d because it shelled out to {@code wget}).
 * Downloads over plain HTTP instead, so it runs anywhere.
 */
@Test(groups = "ui")
public class FileDownloadTests extends UiBaseTest {

    @Test(description = "First listed file downloads with content")
    public void downloadFile() {
        FileDownloadPage downloadPage = new HomePage(driver()).open().goTo("file download");
        Path dir = FileHelper.downloadDir();
        FileHelper.cleanDirectory(dir);

        assertThat(downloadPage.availableFiles())
                .as("Expected at least one file to download")
                .isNotEmpty();

        Path downloaded = downloadPage.downloadFirstFile(dir);

        assertThat(Files.exists(downloaded)).isTrue();
        assertThat(downloaded.toFile().length()).isGreaterThan(0);
    }
}
