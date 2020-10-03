package org.meowcat.minigame.maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Itsusinn
 */
public class Main {

    private static int width;

    private static int height;

    private static File outputFile;

    private static Maze.WallType wallType;

    public static void main(String[] args) throws IOException {
        parseArgs(args);
        Maze maze = new Maze(width, height);
        MazeBuilder mazeBuilder = new MazeBuilder(maze);
        mazeBuilder.process();
        String mazeString = maze.print(wallType);
        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            fileWriter.write(mazeString);
        }
    }

    private static void parseArgs(final String[] args) {
        if (args.length != 8) {
            System.out.println("Usage: -w {width} -h {height} -out {output_file} -wall {DIAMOND,SQUARE}");
            System.out.println("Example: -w 50 -h 100 -out maze_100_100.txt -wall SQUARE");
            System.out.println("Attention: -Dfile.encoding=UTF-8 needed");
            System.exit(0);
        }
        for (int i = 0; i < args.length; i += 1) {
            String arg = args[i];
            switch (arg) {
                case "-w":
                    width = Integer.parseInt(args[i + 1]);
                    break;
                case "-h":
                    height = Integer.parseInt(args[i + 1]);
                    break;
                case "-out":
                    outputFile = new File(args[i + 1]);
                    break;
                case "-wall":
                    wallType = Maze.WallType.valueOf(args[i + 1]);
                    break;
            }
        }
    }
}

