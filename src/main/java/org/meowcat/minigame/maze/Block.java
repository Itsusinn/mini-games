package org.meowcat.minigame.maze;

import lombok.Data;
import java.util.ArrayList;

/**
 * @author Itsusinn
 */
@Data
public class Block {

    final static int WALL = 1;
    final static int WAY = 0;
    private int type;
    private int row,column,r,c;
    private Block upWall,downWall,rightWall,leftWall;
    private boolean visited;

    public Block(int row, int column, int type){
        this.row = row;
        this.column = column;
        this.type = type;
    }

    /**
     * 获取该方块四个方向中任意一个非边界墙
     */
    public void addWall(ArrayList<Block> wallList){
        if (this.upWall != null){
            wallList.add(this.upWall);
        }
        if (this.downWall != null){
            wallList.add(this.downWall);
        }
        if (this.rightWall != null){
            wallList.add(this.rightWall);
        }
        if (this.leftWall != null){
            wallList.add(this.leftWall);
        }
    }

}