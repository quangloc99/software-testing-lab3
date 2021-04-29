package ru.ifmo.se.s267880.softwareTesting.lab3.utils.ru.ifmo.se.s267880.softwareTesting.lab3.tests;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.function.Supplier;

@RunWith(Parameterized.class)
public class TestHelloWorld extends TestTemplate {
    public TestHelloWorld(Supplier<WebDriver> driverSupplier) throws Exception {
        super(driverSupplier);
    }

    @Override
    protected void setupGit() throws IOException, GitAPIException {
        super.setupGit();
        newRepo("A cool repo for test hello world program in various language");

        changeLocalGitFile("/cpp/hello.cpp",
                "#include<iostream>\n" +
                        "\n" +
                        "int main() {\n" +
                        "    std::cout << \"Hello world\" << std::endl;\n" +
                        "}");
        changeLocalGitFile("/c/hello.c",
                "#include<stdio.h>\n" +
                        "\n" +
                        "int main() {\n" +
                        "    printf(\"Hello world\");\n" +
                        "}");

        changeLocalGitFile("/python/hello.py",
                "print(\"Hello world\")");

        changeLocalGitFile("/js/hello.js", "console.log(\"Hello world\")");
        changeLocalGitFile("/java/Hello.java",
                "public class Hello {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello world\");\n" +
                        "    }\n" +
                        "}");
        git.commit().setMessage("Add hello worlds in various languages").call();
        doPush();
    }

    @Test
    public void testHelloWorld() throws Exception {
        navigateToFileThenCompare("/cpp/hello.cpp",
                "#include<iostream>\n" +
                        "\n" +
                        "int main() {\n" +
                        "    std::cout << \"Hello world\" << std::endl;\n" +
                        "}");
        navigateToFileThenCompare("/c/hello.c",
                "#include<stdio.h>\n" +
                        "\n" +
                        "int main() {\n" +
                        "    printf(\"Hello world\");\n" +
                        "}");

        navigateToFileThenCompare("/python/hello.py",
                "print(\"Hello world\")");

        navigateToFileThenCompare("/js/hello.js", "console.log(\"Hello world\")");
        navigateToFileThenCompare("/java/Hello.java",
                "public class Hello {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello world\");\n" +
                        "    }\n" +
                        "}");
    }
}
