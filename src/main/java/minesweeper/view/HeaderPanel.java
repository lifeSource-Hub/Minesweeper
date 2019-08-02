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

import minesweeper.controller.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class HeaderPanel extends JPanel implements ActionListener
{
    private ButtonIcon icon;
    private JButton smiley;
    private Controllable controllable;
    private MouseAdapter mAdapter;

    private JMenuItem mItemBeginner;
    private JMenuItem mItemIntermediate;
    private JMenuItem mItemExpert;

    HeaderPanel()
    {
        // setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        // setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        setLayout(new BorderLayout());

        icon = new ButtonIcon();
        smiley = new JButton(icon.SMILEY);

        smiley.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        smiley.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    smiley.setIcon(icon.SMILEY_PRESSED);
                    // Logger.debug("\nSmiley pressed");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    if (e.getPoint().x >= 0
                            && e.getPoint().x <= smiley.getIcon().getIconWidth()
                            && e.getPoint().y >= 0
                            && e.getPoint().y <= smiley.getIcon().getIconHeight())
                    {
                        // Logger.debug("Smiley Released");
                        controllable.controlEvent(new CommandEvent(Command.NEW));
                    }
                    else
                    {
                        // Logger.debug("Mouse release failure!");
                    }

                    smiley.setIcon(icon.SMILEY);
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem mItemNew = new JMenuItem(Command.NEW.getLabel());
        mItemBeginner = new JMenuItem(Command.BEGINNER.getLabel());
        mItemIntermediate = new JMenuItem(Command.INTERMEDIATE.getLabel());
        mItemExpert = new JMenuItem(Command.EXPERT.getLabel());
        JMenuItem mItemExit = new JMenuItem(Command.EXIT.getLabel());

        mItemNew.setActionCommand(Command.NEW.name());
        mItemBeginner.setActionCommand(Command.BEGINNER.name());
        mItemIntermediate.setActionCommand(Command.INTERMEDIATE.name());
        mItemExpert.setActionCommand(Command.EXPERT.name());
        mItemExit.setActionCommand(Command.EXIT.name());

        gameMenu.add(mItemNew);
        gameMenu.addSeparator();
        gameMenu.add(mItemBeginner);
        gameMenu.add(mItemIntermediate);
        gameMenu.add(mItemExpert);
        gameMenu.addSeparator();
        gameMenu.add(mItemExit);

        addCommandListeners(gameMenu);
        menuBar.add(gameMenu);

        JPanel smileyPnl = new JPanel();
        smileyPnl.setLayout(new BoxLayout(smileyPnl, BoxLayout.LINE_AXIS));

        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        smileyPnl.setBorder(BorderFactory.createCompoundBorder(loweredBevel, emptyBorder));
        // smileyPnl.setBorder(BorderFactory.createLoweredBevelBorder());

        smileyPnl.add(Box.createHorizontalGlue());
        smileyPnl.add(smiley);
        smileyPnl.add(Box.createHorizontalGlue());

        add(menuBar, BorderLayout.NORTH);
        // add(Box.createRigidArea(new Dimension(0, 5)));
        add(smileyPnl, BorderLayout.CENTER);
        // add(Box.createRigidArea(new Dimension(0, 5)));

        setMenuIcon(Command.BEGINNER);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Logger.debug(e.getActionCommand());
        Command selection = Command.getCommand(e.getActionCommand());

        controllable.controlEvent(new CommandEvent(selection));
    }

    // TODO make setMenuIcon more efficient
    public void setMenuIcon(Command difficulty)
    {
        switch (difficulty)
        {
            case BEGINNER:
                mItemBeginner.setIcon(icon.CHECK);
                mItemIntermediate.setIcon(null);
                mItemExpert.setIcon(null);
                break;
            case INTERMEDIATE:
                mItemBeginner.setIcon(null);
                mItemIntermediate.setIcon(icon.CHECK);
                mItemExpert.setIcon(null);
                break;
            case EXPERT:
                mItemBeginner.setIcon(null);
                mItemIntermediate.setIcon(null);
                mItemExpert.setIcon(icon.CHECK);
                break;
        }
    }

    private void addCommandListeners(JMenu menu)
    {
        for (Component menuComponent : menu.getMenuComponents())
        {
            if (menuComponent instanceof JMenuItem)
            {
                ((JMenuItem) menuComponent).addActionListener(this);
                ((JMenuItem) menuComponent).setHorizontalTextPosition(SwingConstants.LEFT);
            }
        }
    }

    void setControllable(Controllable controllable)
    {
        this.controllable = controllable;
    }

    public void setMAdapter(MouseAdapter mAdapter)
    {
        this.mAdapter = mAdapter;
    }
}
