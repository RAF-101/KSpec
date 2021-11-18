package Spec;

import java.io.*;
import java.nio.file.Files;

public class main {
    public static void main(String[] args) throws IOException {
        File konfig = new File("src/main/java/Spec/konfig.json");
        Config config = new Config(konfig);

        System.out.println(Files.readString(konfig.toPath()).toString());
        System.out.println(config.getJSONForm());
        System.out.println(Files.readString(konfig.toPath()).toString().equals(config.getJSONForm()));
    }
}
