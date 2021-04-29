package ru.ifmo.se.s267880.softwareTesting.lab3.utils.ru.ifmo.se.s267880.softwareTesting.lab3.tests;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.CommitBrowsingPage;

import java.io.IOException;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestWithCommitHistoryComplex extends TestTemplate {
    public TestWithCommitHistoryComplex(Supplier<WebDriver> driverSupplier) throws Exception {
        super(driverSupplier);
    }

    @Override
    public void setupGit() throws IOException, GitAPIException {
        super.setupGit();
        newRepo("A very very cool repo for testing a complex project with commit history");

        // If you don't know the reference, this is the Konami code
        changeLocalGitFile("/the/first/file.txt", "UP UP");
        changeLocalGitFile("/the/second/file.txt", "DOWN DOWN");
        git.commit().setMessage("Add 2 files").call();

        changeLocalGitFile("/the/first/file.txt", "LEFT RIGHT");
        git.commit().setMessage("Change /the/first/file.txt").call();

        changeLocalGitFile("/the/second/file.txt", "LEFT RIGHT");
        git.commit().setMessage("Change /the/second/file.txt").call();

        changeLocalGitFile("/the/first/file.txt", "BA<START>");
        changeLocalGitFile("/the/second/file.txt", "BA<START>");
        git.commit().setMessage("Complete the combo!!!").call();

        doPush();
    }

    @Test
    public void test() throws Exception {
        CommitBrowsingPage commits;
        CommitBrowsingPage.CommitElement pickedCommit;
        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(3);
        assertEquals(pickedCommit.getCommitShortMessage(), "Add 2 files");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/the/first/file.txt", "UP UP");
        navigateToFileThenCompare("/the/second/file.txt", "DOWN DOWN");

        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(2);
        assertEquals(pickedCommit.getCommitShortMessage(), "Change /the/first/file.txt");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/the/first/file.txt", "LEFT RIGHT");
        navigateToFileThenCompare("/the/second/file.txt", "DOWN DOWN");

        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(1);
        assertEquals(pickedCommit.getCommitShortMessage(), "Change /the/second/file.txt");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/the/first/file.txt", "LEFT RIGHT");
        navigateToFileThenCompare("/the/second/file.txt", "LEFT RIGHT");

        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(0);
        assertEquals(pickedCommit.getCommitShortMessage(), "Complete the combo!!!");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/the/first/file.txt", "BA<START>");
        navigateToFileThenCompare("/the/second/file.txt", "BA<START>");
    }
}
