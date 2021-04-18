package com.yatskiv;

import com.yatskiv.textprocessor.FileTextProcessor;
import com.yatskiv.textprocessor.TextProcessor;
import com.yatskiv.textprocessor.analyze.LineMatcher;
import com.yatskiv.textprocessor.print.ConsolePrinter;
import com.yatskiv.textprocessor.print.FilePrinter;
import org.apache.commons.cli.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class TextAnalyzer {

    public static void main(String... args) {
        Options options = new Options();
        options.addOption(
                Option.builder("in")
                        .required(true)
                        .hasArg(true)
                        .desc("Input file name")
                        .longOpt("input")
                        .build()
        );
        options.addOption(
                Option.builder("m")
                        .required(true)
                        .hasArg(true)
                        .desc("CSV string of matches to analyze")
                        .longOpt("matches")
                        .build()
        );
        options.addOption(
                Option.builder("out")
                        .required(false)
                        .hasArg(true)
                        .desc("Output file name, if not specified - result will be printed to console")
                        .longOpt("output")
                        .build()
        );
        options.addOption(
                Option.builder("s")
                        .required(false)
                        .hasArg(false)
                        .desc("Skip the empty lines")
                        .longOpt("skipempty")
                        .build()
        );
        options.addOption(
                Option.builder("g")
                        .required(false)
                        .hasArg(false)
                        .desc("Global char offset")
                        .longOpt("globaloffset")
                        .build()
        );

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Could not parse the command: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar TextAnalyzer.jar", options);
            return;
        }

        String inFileName = cmd.getOptionValue("in");
        List<String> matches = List.of(cmd.getOptionValue("m").split("\\s*,\\s*"));
        String outFileName = cmd.getOptionValue("out");

        int config = 0;
        config |= cmd.hasOption("s") ? FileTextProcessor.SKIP_EMPTY_LINES : 0;
        config |= cmd.hasOption("g") ? FileTextProcessor.GLOBAL_CHAR_OFFSET : 0;

        Instant start = Instant.now();

        TextProcessor processor = new FileTextProcessor(inFileName, matches);
        try {
            processor.init(config);
            processor.analyze(new LineMatcher());
            processor.printResults(outFileName != null ? new FilePrinter(outFileName) : new ConsolePrinter());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Execution time: " + (Duration.between(start, Instant.now()).toMillis() / 1000.0) + " s");
    }

}
