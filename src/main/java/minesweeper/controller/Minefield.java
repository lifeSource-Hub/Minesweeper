package minesweeper.controller;

import org.pmw.tinylog.Logger;

import java.util.Random;

public class Minefield
{
    private MinefieldPlot[][] field;
    private Random random;
    private int totalMineCount;

    public Minefield(int fieldRows, int fieldCols)
    {
        field = new MinefieldPlot[fieldRows][fieldCols];
        random = new Random();
        boolean randomBool;

        for (int row = 0; row < field.length; row++)
        {
            for (int col = 0; col < field[row].length; col++)
            {
                randomBool = getRandomBool();
                field[row][col] = new MinefieldPlot(randomBool);

                if (randomBool)
                {
                    totalMineCount++;
                }
            }
        }

        countAdjacentMines();
        Logger.debug("Total mine count: " + totalMineCount);
    }

    public MinefieldPlot getPlot(int x, int y)
    {
        return field[x][y];
    }

    public int getMineCount(int x, int y)
    {
        return field[x][y].getAdjacentMines();
    }

    private boolean getRandomBool()
    {
        // Odds of returning false (setting a mine) are 1:6
        return random.nextInt(6) == 0;
    }

    public int getTotalMineCount()
    {
        return totalMineCount;
    }

    private void countAdjacentMines()
    {
        for (int row = 0; row < field.length; row++)
        {
            for (int col = 0; col < field[row].length; col++)
            {
                int adjacentMines = 0;

                for (int rowDelta = row - 1; rowDelta < row + 2; rowDelta++)
                {
                    for (int colDelta = col - 1; colDelta < col + 2; colDelta++)
                    {
                        // Array boundary check
                        if ((rowDelta < field.length && colDelta < field[row].length)
                                && (rowDelta >= 0 && colDelta >= 0)
                                && (rowDelta != row || colDelta != col))
                        {
                            if (field[rowDelta][colDelta].isMined())
                            {
                                adjacentMines++;
                            }
                        }
                    }
                }

                field[row][col].setAdjacentMines(adjacentMines);
            }
        }
    }

    // For counting neighboring mines of a single mine
    // public int getMineCount(int x, int y)
    // {
    //     int adjacentMines = 0;
    //
    //     for (int row = x - 1; row < x + 2; row++)
    //     {
    //         for (int col = y - 1; col < y + 2; col++)
    //         {
    //             // Array boundary check
    //             if ((row >= 0 && col >= 0)
    //                     && (row < field.length && col < field[row].length)
    //                     && (row != x || col != y))
    //             {
    //                 if (field[row][col].isMined())
    //                 {
    //                     adjacentMines++;
    //                 }
    //             }
    //         }
    //     }
    //
    //     return adjacentMines;
    // }
}
