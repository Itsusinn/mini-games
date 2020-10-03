package org.meowcat.minigame.maze

import java.util.*

/**
 * @author Itsusinn
 */
data class Block(val row: Int, val column: Int, var type: Int) {
    var r = 0
    var c = 0
    var upWall: Block? = null
    var downWall: Block? = null
    var rightWall: Block? = null
    var leftWall: Block? = null
    var isVisited = false

    fun addWall(wallList: ArrayList<Block?>) {
        if (upWall != null) {
            wallList.add(upWall)
        }
        if (downWall != null) {
            wallList.add(downWall)
        }
        if (rightWall != null) {
            wallList.add(rightWall)
        }
        if (leftWall != null) {
            wallList.add(leftWall)
        }
    }

    companion object {
        const val WALL = 1
        const val WAY = 0
    }
}