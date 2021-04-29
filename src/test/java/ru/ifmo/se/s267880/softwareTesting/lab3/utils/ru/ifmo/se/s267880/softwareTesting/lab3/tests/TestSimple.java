package ru.ifmo.se.s267880.softwareTesting.lab3.utils.ru.ifmo.se.s267880.softwareTesting.lab3.tests;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.function.Supplier;

@RunWith(Parameterized.class)
public class TestSimple extends TestTemplate {
    public TestSimple(Supplier<WebDriver> driverSupplier) throws Exception {
        super(driverSupplier);
    }

    @Override
    protected void setupGit() throws IOException, GitAPIException {
        super.setupGit();
        newRepo("Very simple repo for testing");
        changeLocalGitFile("a.txt", "Never gonna give you up!");
        git.commit().setMessage("Very first commit").call();
        doPush();
    }

    @Test
    public void simpleTest() throws Exception {
        navigateToFileThenCompare("/a.txt", "Never gonna give you up!");
    }
}
