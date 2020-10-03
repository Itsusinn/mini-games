package org.meowcat.minigame.maze;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Itsusinn
 */

public class Maze {

    private static final Map<WallType, Character[]> CHARSETS;
    private Block[][] maze;
    private Block[][] wayMaze;
    private int r;
    private int c;
    private int row;
    private int column;
    final static int UP = 0,DOWN =1,RIGTH = 2,LEFT = 3;

    static {
        CHARSETS = new HashMap<>();
        CHARSETS.put(WallType.DIAMOND, new Character[] {'◆', '◇'});
        CHARSETS.put(WallType.SQUARE, new Character[] {'█', ' '});
    }

    public int[][] getIntMaze(){
        int[][] intMaze = new int[this.row][this.column];
        for (int i=0; i < row; i++){
            for (int j = 0;j < column;j++){
                intMaze[i][j] = maze[i][j].getType();
            }
        }
        return intMaze;
    }
    public String print(){
        return print(WallType.DIAMOND);
    }

    public String print(final WallType wallType){
        StringBuilder sb = new StringBuilder();
        Character[] walls = CHARSETS.get(wallType);
        for(int i = 0; i < this.row; i++){
            for (int j = 0;j < this.column;j++){
                if (this.maze[i][j].getType()==1){
                    sb.append(walls[0]).append(" ");
                }else{
                    sb.append(walls[1]).append(" ");
                }

            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * The maze initialization r, c refers to the row and column of
     * the road blocks, so the actual size of the maze is r*2+1, c*2+1
     * @param r 道路方块的行
     * @param c 道路方块的列
     */
    public Maze(int r,int c){
        this.r = r;
        this.c = c;
        this.row = r*2+1;
        this.column = c*2+1;
        this.maze = new Block[row][column];
        this.wayMaze = new Block[r][c];
        //Fill maze with wall
        for (int i=0; i < row; i++){
            for (int j = 0;j < column;j++){
                maze[i][j] = new Block(i,j,Block.WALL);
            }
        }
        //init wayMaze that refers to a set of blocks around eight other blocks
        for (int i=0;i < r;i++){
            for (int j=0;j<c;j++){
                wayMaze[i][j] = maze[2*i+1][2*j+1];
                wayMaze[i][j].setR(i);
                wayMaze[i][j].setC(j);
            }
        }

        //let every block remember its neighbours
        for (int i=0;i < this.r;i++){
            for (int j=0;j< this.c;j++){
                Block block = this.wayMaze[i][j];
                int i2 = 2*i+1,j2 = 2*j+1;
                if (i<r-1){
                    block.setUpWall(maze[i2+1][j2]);}
                if (i>0){
                    block.setDownWall(maze[i2-1][j2]);
                }
                if (j<c-1){
                    block.setRightWall(maze[i2][j2+1]);
                }
                if (j>0){
                    block.setLeftWall(maze[i2][j2-1]);
                }
            }
        }
    }


    /**
     * 获取二级迷宫某一位置的方块
     * @param r 二级行坐标
     * @param c 二级列坐标
     * @return 该位置的方块
     */
    public Block getWayBlock(int r,int c){
        return wayMaze[r][c];
    }

    /**
     * Determine whether the cells on both sides of the wall have been visited
     * @param wall 墙
     * @return
     */
    public boolean isAllVisited(Block wall){
        int row =wall.getRow();
        int column = wall.getColumn();
        boolean odd = Maze.isOdd(wall.getRow());
        if(odd){
            Block upBlock = maze[row+1][column];
            Block downBlock = maze[row-1][column];
            if(upBlock.isVisited()&&downBlock.isVisited()){
                return true;
            }return false;
        }else{
            Block leftBlock = maze[row][column-1];
            Block rightBlock = maze[row][column+1];
            if(leftBlock.isVisited()&&rightBlock.isVisited()){
                return true;
            }return false;
        }
    }

    /**
     * Get the unvisited road on both sides of the wall
     * @param wall
     * @return unvisited road
     */
    public Block getUnvisitedBlock(Block wall){
        int row =wall.getRow();
        int column = wall.getColumn();
        boolean odd = Maze.isOdd(wall.getRow());
        if(odd){
            Block upBlock = maze[row+1][column];
            Block downBlock = maze[row-1][column];
            if(upBlock.isVisited()){
                return downBlock;
            }else{
                return upBlock;
            }
        }else{
            Block leftBlock = maze[row][column-1];
            Block rightBlock = maze[row][column+1];
            if(leftBlock.isVisited()){
                return rightBlock;
            }else {
                return leftBlock;
            }
        }
    }


    //Determine whether a number is odd or even
    private static boolean isOdd(int a){
        if((a&1) != 1){
            return true;
        }
        return false;
    }

    public enum WallType {
        DIAMOND,
        SQUARE
    }

    public int getR() {
        return r;
    }
    public int getC() {
        return c;
    }
}
