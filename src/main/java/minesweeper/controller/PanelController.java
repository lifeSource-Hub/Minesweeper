package minesweeper.controller;

import minesweeper.view.GridPanel;
import minesweeper.view.HeaderPanel;

import javax.swing.*;

public class PanelController
{
    private HeaderPanel headerPnl;
    private GridPanel gridPnl;

    public PanelController()
    {}

    public void setHeaderPnl(HeaderPanel headerPnl)
    {
        this.headerPnl = headerPnl;
    }

    public void setGridPnl(GridPanel gridPnl)
    {
        this.gridPnl = gridPnl;
    }

    public void setHeaderGameOver(boolean victory)
    {
        headerPnl.gameOver(victory);
    }

    public void newGame()
    {
        newGame(gridPnl.getDifficulty());
    }

    public void newGame(Command selection)
    {
        gridPnl.setupGrid(selection);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gridPnl);
        frame.pack();
    }
}
