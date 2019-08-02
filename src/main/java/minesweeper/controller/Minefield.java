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
package minesweeper.controller;

import java.util.Random;

public class Minefield
{
    private final int FIELD_ROWS;
    private final int FIELD_COLS;
    private MinefieldPlot[][] field;
    private Random random;

    public Minefield(int fieldRows, int fieldCols)
    {
        this.FIELD_ROWS = fieldRows;
        this.FIELD_COLS = fieldCols;
        field = new MinefieldPlot[FIELD_ROWS][FIELD_COLS];
        random = new Random();

        for (int row = 0; row < field.length; row++)
        {
            for (int col = 0; col < field[row].length; col++)
            {
                field[row][col] = new MinefieldPlot(getRandomBool());
            }
        }

        countAdjacentMines();
    }

    public MinefieldPlot getPlot(int x, int y)
    {
        return field[x][y];
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

    public int getMineCount(int x, int y)
    {
        return field[x][y].getAdjacentMines();
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

    private boolean getRandomBool()
    {
        return random.nextInt(6) == 0;
    }
}
