package minesweeper.controller;

import minesweeper.view.GridPanel;
import minesweeper.view.HeaderPanel;

import javax.swing.*;

public class PanelController
{
    private HeaderPanel headerPnl;
    private GridPanel gridPanel;

    public PanelController()
    {}

    public void setHeaderPnl(HeaderPanel headerPnl)
    {
        this.headerPnl = headerPnl;
    }

    public void setGridPnl(GridPanel gridPanel)
    {
        this.gridPanel = gridPanel;
    }

    public void setSmiley(ImageIcon smiley_dead)
    {
        headerPnl.setSmiley(smiley_dead);
    }

    public void setMenuDifficultyIcon(Command difficulty)
    {
        headerPnl.setMenuDifficultyIcon(difficulty);
    }

    public void newGame()
    {
        newGame(gridPanel.getDifficulty());
    }

    public void newGame(Command selection)
    {
        gridPanel.setupGrid(selection);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gridPanel);
        frame.pack();
    }
}
