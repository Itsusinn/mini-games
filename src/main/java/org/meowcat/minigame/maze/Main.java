package org.meowcat.minigame.maze;

/**
 * @author Itsusinn
 */
public class Main {
    public static void main(String[] args){
        Maze maze = new Maze(15,15);
        MazeBuilder mazeBuilder = new MazeBuilder(maze);
        mazeBuilder.process();
        maze.print();
    }
}

