package minesweeper.view;

import minesweeper.controller.Command;
import minesweeper.controller.PanelController;
import minesweeper.controller.Minefield;
import minesweeper.controller.MinefieldPlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// TODO win condition
public class GridPanel extends JPanel
{
    // TODO create game difficulty class which stores current state
    private Command difficulty;
    private Minefield minefield;
    private JLabel[][] gridLbl;
    private ButtonIcon icon;
    private PanelController panelController;

    GridPanel()
    {
        this(Command.BEGINNER);
    }

    GridPanel(Command difficulty)
    {
        icon = new ButtonIcon();

        setupGrid(difficulty);
    }

    public void setupGrid(Command difficulty)
    {
        this.removeAll();
        this.difficulty = difficulty;
        int gridRows = difficulty.getHeight();
        int gridCols = difficulty.getWidth();

        gridLbl = new JLabel[gridRows][gridCols];
        minefield = new Minefield(gridRows, gridCols);

        setLayout(new GridLayout(gridRows, gridCols));

        initializeGrid();
        // panelController.setMenuDifficultyIcon(difficulty);
        revalidate();
        repaint();
    }

    public void setPanelController(PanelController panelController)
    {
        this.panelController = panelController;
    }

    public Command getDifficulty()
    {
        return difficulty;
    }
    // TODO should we be creating & adding a new grid each time? size may change

    private void initializeGrid()
    {
        for (int i = 0; i < gridLbl.length; i++)
        {
            for (int j = 0; j < gridLbl[i].length; j++)
            {
                gridLbl[i][j] = new JLabel(icon.DEFAULT);
                gridLbl[i][j].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

                add(gridLbl[i][j]);

                final int ROW = i;
                final int COL = j;

                gridLbl[i][j].addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent event)
                    {
                        handleMouseEvent(event, ROW, COL);
                    }
                });
            }
        }
    }

    private void handleMouseEvent(MouseEvent event, int row, int col)
    {
        boolean flagged = minefield.getPlot(row, col).isFlagged();
        JLabel eventLbl = gridLbl[row][col];

        if (SwingUtilities.isLeftMouseButton(event) && !flagged)
        {
            // Logger.debug("Label left clicked at row " + row + ", col " + col);

            if (minefield.getPlot(row, col).isMined())
            {
                loseGame(eventLbl);
            }
            else
            {
                setLabelIcon(row, col);
            }
        }
        else if (SwingUtilities.isRightMouseButton(event))
        {
            // Logger.debug("Label right clicked at row " + row + ", col " + col);
            minefield.getPlot(row, col).toggleFlag();

            if (flagged)
            {
                removeFlag(eventLbl);
            }
            else
            {
                displayFlag(eventLbl);
            }
        }
    }

    private void setLabelIcon(int row, int col)
    {
        int mineCount = minefield.getMineCount(row, col);
        // Logger.debug("Mine count: " + minefield.getMineCount(row, col) + "\n");

        if (mineCount > 0)
        {
            ImageIcon newIcon;

            switch (mineCount)
            {
                case 1:
                    newIcon = icon.ONE;
                    break;
                case 2:
                    newIcon = icon.TWO;
                    break;
                case 3:
                    newIcon = icon.THREE;
                    break;
                case 4:
                    newIcon = icon.FOUR;
                    break;
                case 5:
                    newIcon = icon.FIVE;
                    break;
                case 6:
                    newIcon = icon.SIX;
                    break;
                case 7:
                    newIcon = icon.SEVEN;
                    break;
                case 8:
                    newIcon = icon.EIGHT;
                    break;
                default:
                    newIcon = icon.MISFLAGGED_SPACE;
            }

            gridLbl[row][col].setIcon(newIcon);
        }
        else
        {
            gridLbl[row][col].setIcon(icon.EMPTY);
            extend(row, col);
        }

        removeMListeners(gridLbl[row][col]);

        // TODO victory condition
        // victory condition == all spaces without a mine are uncovered (doesn't matter if mines are marked)
        //      use a counter?
        // a) compare (total spaces - total mine count) to uncovered spaces
        // b) compare total remaining covered spaces to total mine count
    }

    private void extend(int row, int col)
    {
        for (int i = row - 1; i < row + 2; i++)
        {
            for (int j = col - 1; j < col + 2; j++)
            {
                // Array out of bounds check
                if ((i < gridLbl.length && j < gridLbl[row].length)
                        && (i >= 0 && j >= 0))
                {
                    // Ensure there is no mine and it has not already been set
                    if (!(minefield.getPlot(i, j).isMined())
                            && gridLbl[i][j].getIcon() == icon.DEFAULT)
                    {
                        setLabelIcon(i, j);
                    }
                }
            }
        }
    }

    private void displayFlag(JLabel label)
    {
        label.setIcon(icon.FLAG);
    }

    private void removeFlag(JLabel label)
    {
        label.setIcon(icon.DEFAULT);
    }

    private void loseGame(JLabel clickedLbl)
    {
        for (int i = 0; i < gridLbl.length; i++)
        {
            for (int j = 0; j < gridLbl[i].length; j++)
            {
                MinefieldPlot plot = minefield.getPlot(i, j);

                if (plot.isMined() && !(plot.isFlagged()))
                {
                    gridLbl[i][j].setIcon(icon.UNFLAGGED_MINE);
                }
                else if (!(plot.isMined()) && plot.isFlagged())
                {
                    gridLbl[i][j].setIcon(icon.MISFLAGGED_SPACE);
                }

                removeMListeners(gridLbl[i][j]);
            }
        }

        clickedLbl.setIcon(icon.EXPLODED);
        panelController.setSmiley(icon.SMILEY_DEAD);
    }

    private void removeMListeners(JLabel label)
    {
        for (MouseListener mListener : label.getMouseListeners())
        {
            label.removeMouseListener(mListener);
        }
    }
}
