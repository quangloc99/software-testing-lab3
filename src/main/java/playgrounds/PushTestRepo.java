package playgrounds;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class PushTestRepo {
    public static void main(String[] args) throws Exception  {
        Git git = Git.open(new File("./test-git"));

        git.remoteAdd()
                .setName("origin")
                .setUri(new URIish("https://github.com/quangloc99-testaccount/test-repo.git"))
                .call();

        git.push()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(
                        "quangloc99-testaccount",
                        "gX4rf7OZuV2b"
                ))
                .call();
    }
}
