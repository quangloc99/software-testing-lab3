package ru.ifmo.se.s267880.softwareTesting.lab3.utils.ru.ifmo.se.s267880.softwareTesting.lab3.tests;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.function.Supplier;

@RunWith(Parameterized.class)
public class TestWithFolders extends TestTemplate {
   public TestWithFolders(Supplier<WebDriver> driverSupplier) throws Exception {
       super(driverSupplier);
   }

   @Override
   protected void setupGit() throws GitAPIException, IOException {
       super.setupGit();
       newRepo("Very simple repo for testing with folder");
       changeLocalGitFile("/at-root.txt", "at root");
       changeLocalGitFile("/parent/a.txt", "A file");
       changeLocalGitFile("/parent/b.txt", "B file");
       changeLocalGitFile("/parent/parent2/c.txt", "C file");
       changeLocalGitFile("/parent3/d.txt", "D file");
       git.commit().setMessage("Add files").call();
       doPush();
   }

   @Test
   public void test() throws Exception {
       navigateToFileThenCompare("/at-root.txt", "at root");
       navigateToFileThenCompare("/parent/a.txt", "A file");
       navigateToFileThenCompare("/parent/b.txt", "B file");
       navigateToFileThenCompare("/parent/parent2/c.txt", "C file");
       navigateToFileThenCompare("/parent3/d.txt", "D file");
   }
}
