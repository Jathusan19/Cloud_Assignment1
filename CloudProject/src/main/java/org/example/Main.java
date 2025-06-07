package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java -jar <jar-file> <input-file-path> <output-directory>");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputDir = args[1];

        if (!outputDir.endsWith(File.separator)) {
            outputDir += File.separator;
        }

        ViewsPerSubscriberReducer.run(inputPath, outputDir + "reducer1_output.xlsx");
    }
}
