package playgrounds;

import ru.ifmo.se.s267880.softwareTesting.lab3.utils.LoadPropertiesToSystem;

import java.io.IOException;

public class TestLoadConfig {
    public static void main(String[] args) throws Exception {
        LoadPropertiesToSystem.doLoad();

        System.out.println(System.getProperty("github-username"));
        System.out.println(System.getProperty("github-password"));
    }
}
