package org.meowcat.minigame.maze;

import java.util.ArrayList;

/**
 * @author Itsusinn
 */
public class MazeBuilder {
    private Maze maze;
    Block currentBlock;

    public MazeBuilder(Maze maze){
        this.maze =maze;
    }

    public void process(){
        double r1 = Math.random()* maze.getR();
        int r2 = (int)r1;
        double c1 =Math.random()* maze.getC();
        int c2 = (int)c1;
        currentBlock = maze.getWayBlock(r2,c2);
        currentBlock.setVisited(true);
        currentBlock.setType(Block.WAY);
        ArrayList<Block> wallList = new ArrayList<>();
        currentBlock.addWall(wallList);
        Block wall;
        while(!wallList.isEmpty()){
            double index = wallList.size()*Math.random();
            wall = wallList.get((int)index);
            if(!maze.isAllVisited(wall)){
                wallList.remove(wall);
                wall.setType(Block.WAY);
                currentBlock = maze.getUnvisitedBlock(wall);
                currentBlock.setVisited(true);
                currentBlock.setType(Block.WAY);
                currentBlock.addWall(wallList);
            }else{
                wallList.remove(wall);
            }
        }
    }
}
