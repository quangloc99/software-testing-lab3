package ru.ifmo.se.s267880.softwareTesting.lab3.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class LoadPropertiesToSystem {
    public static final String propertiesPathName = "config.properties";
    private static boolean loaded = false;

    public static boolean doLoad() throws IOException {
        if (loaded) {
            return false;
        }
        var prop = new Properties();
        var file = new File(propertiesPathName);
        prop.load(new FileInputStream(file));
        for (var name: prop.stringPropertyNames()) {
            System.setProperty(name, prop.getProperty(name));
        }
        loaded = true;
        return true;
    }
}
