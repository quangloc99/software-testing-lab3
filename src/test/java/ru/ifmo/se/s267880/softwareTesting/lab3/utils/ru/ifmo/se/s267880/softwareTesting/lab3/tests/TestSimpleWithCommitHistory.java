package ru.ifmo.se.s267880.softwareTesting.lab3.utils.ru.ifmo.se.s267880.softwareTesting.lab3.tests;

import static org.junit.Assert.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.CommitBrowsingPage;

import java.io.IOException;
import java.util.function.Supplier;

@RunWith(Parameterized.class)
public class TestSimpleWithCommitHistory extends TestTemplate {
    public TestSimpleWithCommitHistory(Supplier<WebDriver> driverSupplier) throws Exception {
        super(driverSupplier);
    }

    @Override
    protected void setupGit() throws IOException, GitAPIException {
        super.setupGit();
        newRepo("A cool repo for testing a simple file with commit history");

        changeLocalGitFile("/a.txt", "Never gonna give you up!");
        git.commit().setMessage("First line").call();
        changeLocalGitFile("/a.txt", "Never gonna let you down!");
        git.commit().setMessage("Second line").call();
        changeLocalGitFile("/a.txt", "Never gonna run around and desert you!");
        git.commit().setMessage("Third line").call();
        changeLocalGitFile("/a.txt", "Never gonna make you cry!");
        git.commit().setMessage("Forth line").call();
        changeLocalGitFile("/a.txt", "Never gonna say good-bye!");
        git.commit().setMessage("Fifth line").call();
        changeLocalGitFile("/a.txt", "Never gonna tell a lie and hurt you!");
        git.commit().setMessage("Sixth line").call();
        doPush();
    }

    @Test
    public void test() throws Exception {
        CommitBrowsingPage commits;
        CommitBrowsingPage.CommitElement pickedCommit;
        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(5);
        assertEquals(pickedCommit.getCommitShortMessage(), "First line");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/a.txt", "Never gonna give you up!");

        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(4);
        assertEquals(pickedCommit.getCommitShortMessage(), "Second line");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/a.txt", "Never gonna let you down!");

        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(3);
        assertEquals(pickedCommit.getCommitShortMessage(), "Third line");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/a.txt", "Never gonna run around and desert you!");

        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(2);
        assertEquals(pickedCommit.getCommitShortMessage(), "Forth line");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/a.txt", "Never gonna make you cry!");

        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(1);
        assertEquals(pickedCommit.getCommitShortMessage(), "Fifth line");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/a.txt", "Never gonna say good-bye!");

        backToMainRepo();
        commits = rootDirectoryBrowsingPage.openCommitBrowsingPage();
        pickedCommit = commits.getCommitElementList().get(0);
        assertEquals(pickedCommit.getCommitShortMessage(), "Sixth line");
        rootDirectoryBrowsingPage = pickedCommit.browCommit();
        navigateToFileThenCompare("/a.txt", "Never gonna tell a lie and hurt you!");
    }
}
