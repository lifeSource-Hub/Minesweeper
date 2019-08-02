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

    public void toggleFlag()
    {
        hasFlag = !(isFlagged());
    }

    void setAdjacentMines(int adjacentMines)
    {
        this.adjacentMines = adjacentMines;
    }
}
