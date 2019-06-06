package com.vasev.ceco;

import org.apache.commons.cli.*;

public class Main {

    private static Options createOptions() {
        Option size = new Option("s", "size", true, "size of the image");
        Option rect = new Option("r", "rect", true, "coordinate plane");
        Option tasks = new Option("t", "tasks", true, "number of threads");

        Options options = new Options();
        options.addOption(size);
        options.addOption(rect);
        options.addOption(tasks);
        
        return options;
    }

    private static CommandLine createCmd(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args);
        } catch(ParseException ex) {
            throw new IllegalArgumentException("Parsing error: " + ex.getMessage());
        }
    }

    private static Fractal createMandelbrot(CommandLine cmd) {
        int width = 640, height = 480;
        if(cmd.hasOption("s")) {
            String arg = cmd.getOptionValue("s");
            String[] res = arg.split("x");
            width = Integer.parseInt(res[0]);
            height = Integer.parseInt(res[1]);

            if(width < 0 || height < 0) {
                throw new IllegalArgumentException("width and height must be positive integers");
            }
        }

        double[] limits = new double[4];
        limits[0] = -2.0; limits[1] = 2.0; // x
        limits[2] = -2.0; limits[3] = 2.0; // y
        if(cmd.hasOption("r")) {
            String arg = cmd.getOptionValue("r");
            String[] res = arg.split(":");
            limits[0] = Double.parseDouble(res[0]);
            limits[1] = Double.parseDouble(res[1]);
            limits[2] = Double.parseDouble(res[2]);
            limits[3] = Double.parseDouble(res[3]);
        }

        int threadCount = 64;
        if(cmd.hasOption("t")) {
            threadCount = Integer.parseInt(cmd.getOptionValue("t"));

            if(threadCount < 1) {
                throw new IllegalArgumentException("tasks must be bigger than 0");
            }
        }


        return new Fractal(width, height,threadCount);
    }

    public static void startGeneration(String[] args) {
        Options options = createOptions();
        CommandLine cmd = createCmd(options, args);
        Fractal mandelbrot = createMandelbrot(cmd);

        mandelbrot.generate();
    }

    public static void main(String[] args) {
        startGeneration(args);
    }
}
