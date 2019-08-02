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
import minesweeper.controller.CommandEvent;
import minesweeper.controller.Controllable;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame
{
    private HeaderPanel headerPnl;
    private GridPanel gridPnl;

    public AppFrame()
    {
        this("Minesweeper", 600, 800);
    }

    public AppFrame(String title, int width, int height)
    {
        headerPnl = new HeaderPanel();
        gridPnl = new GridPanel();

        headerPnl.setControllable(new Controllable()
        {
            @Override
            public void controlEvent(CommandEvent e)
            {
                // Logger.debug("Action command: " + e.getEvtCommand());

                switch (e.getEvtCommand())
                {
                    case NEW:
                        newGame(gridPnl.getDifficulty());
                        break;
                    case BEGINNER:
                    case INTERMEDIATE:
                    case EXPERT:
                        newGame(e.getEvtCommand());
                        break;
                    case EXIT:
                        System.exit(0);
                        break;
                }
            }
        });

        add(headerPnl, BorderLayout.NORTH);
        add(gridPnl, BorderLayout.CENTER);

        pack();
        setTitle(title);
        setVisible(true);
        // setResizable(false);
        // setLocation(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setIconImage(new ButtonIcon().FRAME_ICON);
    }

    private void newGame(Command difficulty)
    {
        headerPnl.setMenuIcon(difficulty);

        remove(gridPnl);
        gridPnl = new GridPanel(difficulty);
        add(gridPnl);

        revalidate();
        pack();
        repaint();
    }
}
