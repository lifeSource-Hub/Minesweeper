/*
 * Copyright 2019 Nicholas Talbert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minesweeper.view;

import minesweeper.controller.Command;
import minesweeper.controller.Minefield;
import org.pmw.tinylog.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// TODO win condition
class GridPanel extends JPanel
{
    // TODO create game difficulty class which stores current state
    private Command difficulty;
    private final int GRID_ROWS;
    private final int GRID_COLS;
    private Minefield minefield;
    private JLabel[][] gridLbl;
    private ButtonIcon icon;

    GridPanel()
    {
        this(Command.BEGINNER);
    }

    GridPanel(Command newDifficulty)
    {
        this.difficulty = newDifficulty;
        this.GRID_ROWS = difficulty.getHeight();
        this.GRID_COLS = difficulty.getWidth();

        gridLbl = new JLabel[GRID_ROWS][GRID_COLS];
        minefield = new Minefield(GRID_ROWS, GRID_COLS);
        icon = new ButtonIcon();

        setLayout(new GridLayout(GRID_ROWS, GRID_COLS));

        initializeGrid();
    }

    public Command getDifficulty()
    {
        return difficulty;
    }

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
                gameOver(eventLbl);
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
                    newIcon = icon.UNFLAGGED_MINE;
            }

            gridLbl[row][col].setIcon(newIcon);
        }
        else
        {
            gridLbl[row][col].setIcon(icon.EMPTY);
            extend(row, col);
        }

        removeMListeners(gridLbl[row][col]);
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

    private void gameOver(JLabel label)
    {
        // TODO change smiley icon
        for (int i = 0; i < gridLbl.length; i++)
        {
            for (int j = 0; j < gridLbl[i].length; j++)
            {
                if (minefield.getPlot(i, j).isMined())
                {
                    gridLbl[i][j].setIcon(icon.MINE);
                }

                removeMListeners(gridLbl[i][j]);
            }
        }

        label.setIcon(icon.EXPLODED);
    }

    private void removeMListeners(JLabel label)
    {
        for (MouseListener mListener : label.getMouseListeners())
        {
            label.removeMouseListener(mListener);
        }
    }
}
