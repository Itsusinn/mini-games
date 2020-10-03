package org.meowcat.minigame.maze;

import lombok.Data;

/**
 * @author Itsusinn
 */
@Data
public class Maze {

    private Block[][] maze;
    private Block[][] wayMaze;
    private int r;
    private int c;
    private int row;
    private int column;
    final static int UP = 0,DOWN =1,RIGTH = 2,LEFT = 3;

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
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.row; i++){
            for (int j = 0;j < this.column;j++){
                if (this.maze[i][j].getType()==1){
                    sb.append("◆ ");
                }else{
                    sb.append("◇ ");
                }

            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * 迷宫初始化r,c指道路方块的行列,因此迷宫的实际大小是r*2+1,c*2+1
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
        //将迷宫填充为墙壁
        for (int i=0; i < row; i++){
            for (int j = 0;j < column;j++){
                maze[i][j] = new Block(i,j,Block.WALL);
            }
        }
        //挖空九宫格中间的方块,即初始化道路方块,同时纳入道路方块数组
        for (int i=0;i < r;i++){
            for (int j=0;j<c;j++){
                wayMaze[i][j] = maze[2*i+1][2*j+1];
                wayMaze[i][j].setR(i);
                wayMaze[i][j].setC(j);
            }
        }
        //为每个方块设置四个方向的相邻墙壁
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
     * 获取一级迷宫某一位置的方块
     * @param row 行位置
     * @param column 列位置
     * @return 该位置的方块
     */
    public Block getBlock(int row,int column){
        return maze[row][column];
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
     * 判断墙的两边的单元格是否都被访问过
     * @param wall 墙
     * @return
     */
    public boolean isAllVisted(Block wall){
        int row =wall.getRow();
        int column = wall.getColumn();
        boolean odd = Maze.isOdd(wall.getRow());
        if(odd){
            Block upBlock = this.getBlock(row+1,column);
            Block downBlock = this.getBlock(row-1,column);
            if(upBlock.isVisited()&&downBlock.isVisited()){
                return true;
            }return false;
        }else{
            Block leftBlock = this.getBlock(row,column-1);
            Block rightBlock = this.getBlock(row,column+1);
            if(leftBlock.isVisited()&&rightBlock.isVisited()){
                return true;
            }return false;
        }
    }

    /**
     * 得到墙两边中未被访问的路
     * @param wall
     * @return 未被访问的路
     */
    public Block getUnvisitedBlock(Block wall){
        int row =wall.getRow();
        int column = wall.getColumn();
        boolean odd = Maze.isOdd(wall.getRow());
        if(odd){
            Block upBlock = this.getBlock(row+1,column);
            Block downBlock = this.getBlock(row-1,column);
            if(upBlock.isVisited()){
                return downBlock;
            }else{
                return upBlock;
            }
        }else{
            Block leftBlock = this.getBlock(row,column-1);
            Block rightBlock = this.getBlock(row,column+1);
            if(leftBlock.isVisited()){
                return rightBlock;
            }else {
                return leftBlock;
            }
        }
    }

    //判断奇偶数
    private static boolean isOdd(int a){
        if((a&1) != 1){
            return true;
        }
        return false;
    }
}
