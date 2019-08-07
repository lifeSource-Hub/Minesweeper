package minesweeper.controller;

public class MinefieldPlot
{
    private final boolean HAS_MINE;
    private boolean hasFlag;
    private int adjacentMines = 0;

    MinefieldPlot(boolean hasMine)
    {
        this.HAS_MINE = hasMine;
        hasFlag = false;
    }

    public boolean isMined()
    {
        return HAS_MINE;
    }

    public boolean isFlagged()
    {
        return hasFlag;
    }

    int getAdjacentMines()
    {
        return adjacentMines;
    }

    void setAdjacentMines(int adjacentMines)
    {
        this.adjacentMines = adjacentMines;
    }

    public void toggleFlag()
    {
        hasFlag = !(isFlagged());
    }
}
