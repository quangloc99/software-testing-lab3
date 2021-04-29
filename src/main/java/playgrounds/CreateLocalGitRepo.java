package playgrounds;

import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateLocalGitRepo {
    public static final String DIR_URI = "./test-git/";
    public static final File DIR_FILE = new File(DIR_URI);
    public static final Path DIR_PATH = DIR_FILE.toPath();
    public static void main(String[] args) throws Exception {
        deleteDir(DIR_FILE);
        Files.createDirectories(DIR_PATH);

        Git git = Git.init().setDirectory(DIR_FILE).call();

        writeToFile("a.txt", "nice");
        git.add().addFilepattern("a.txt").call();
        git.commit().setMessage("Add a.txt").call();
    }

    public static void writeToFile(String pathname, String content) throws IOException  {
        try (var out = new PrintStream(new FileOutputStream(DIR_URI + pathname))) {
            out.print(content);
        }
    }


    public static void deleteDir(File dir) {
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
