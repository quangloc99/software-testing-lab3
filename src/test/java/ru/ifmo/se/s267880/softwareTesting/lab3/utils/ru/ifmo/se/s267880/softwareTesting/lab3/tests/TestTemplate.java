package ru.ifmo.se.s267880.softwareTesting.lab3.utils.ru.ifmo.se.s267880.softwareTesting.lab3.tests;

import static org.junit.Assert.*;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.FileBrowsingPage;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.NewRepositoryPage;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.RootDirectoryBrowsingPage;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.CookiesStorage;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.WebDriverParameters;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestTemplate {
    public static final String GIT_FOLDER = "./test-git/";
    public static final String MAIN_GITHUB_PAGE = "https://github.com";

    @Parameterized.Parameters
    public static Collection<Object[]> driverParam() {
        return WebDriverParameters.getAvailableWebDriverFromPropertiesAsJunitParam();
    }

    protected WebDriver driver;
    protected Git git;
    protected RootDirectoryBrowsingPage rootDirectoryBrowsingPage;
    protected String repoURL;

    TestTemplate(Supplier<WebDriver> driverSupplier) throws Exception {
        driver = driverSupplier.get();
        driver.manage().window().maximize();
        setupGit();
    }

    protected void setupGit() throws IOException, GitAPIException {
        var gitFolderFile = new File(GIT_FOLDER);
        deleteDir(gitFolderFile);
        Files.createDirectories(gitFolderFile.toPath());
        git = Git.init().setDirectory(gitFolderFile).call();
    }

    protected void changeLocalGitFile(String pathname, String content) throws IOException, GitAPIException {
        if (pathname.startsWith("/")) pathname = pathname.substring(1);
        var trueDir = GIT_FOLDER + pathname;
        new File(trueDir).getParentFile().mkdirs();
        try (var ps = new PrintStream(new FileOutputStream(new File(trueDir)))) {
            ps.print(content);
        }
        git.add().addFilepattern(pathname).call();
    }

    protected void newRepo(String description) throws GitAPIException {
        try {
            String remoteLink = NewRepositoryPage.go(driver)
                    .createWithGithubSuggestedName(description);
            git.remoteAdd().setName("origin").setUri(new URIish(remoteLink)).call();
            repoURL = driver.getCurrentUrl();
            rootDirectoryBrowsingPage = null;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected void navigateToFileThenCompare(String pathname, String content) throws FileNotFoundException  {
        rootDirectoryBrowsingPage.navigateTo(pathname);
        var currentPage = new FileBrowsingPage(driver);
        assertEquals(pathname, currentPage.getPath());
        assertEquals(content, currentPage.getContent());
        rootDirectoryBrowsingPage = currentPage.backToRoot();
    }

    protected void doPush(Consumer<PushCommand> beforePush) throws GitAPIException {
        var pc = git.push().setRemote("origin").setCredentialsProvider(
                new UsernamePasswordCredentialsProvider(
                        System.getProperty("github-username"),
                        System.getProperty("github-password"))
        );
        beforePush.accept(pc);
        pc.call();
        driver.get(repoURL);
        rootDirectoryBrowsingPage = new RootDirectoryBrowsingPage(driver);
        rootDirectoryBrowsingPage.waitTillLoaded();
    }

    protected void doPush() throws GitAPIException {
        doPush(pc -> {});
    }

    protected void backToMainRepo() {
        driver.get(repoURL);

    }

    private static void deleteDir(File dir) {
        // https://attacomsian.com/blog/java-delete-directory-recursively
        File[] files = dir.listFiles();
        if(files != null) {
            for (final File file : files) {
                deleteDir(file);
            }
        }
        dir.delete();
    }
}
