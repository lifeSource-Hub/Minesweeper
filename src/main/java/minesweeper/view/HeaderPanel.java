package minesweeper.view;

import minesweeper.controller.*;
import org.pmw.tinylog.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HeaderPanel extends JPanel // implements ActionListener
{
    private PanelController panelController;
    // private Controllable controllable;
    private MouseAdapter mAdapter;
    private Action menuNewGameHandler;
    private Action menuDifficultyHandler;
    private ButtonIcon icon;
    private JButton smiley;
    private ImageIcon previousSmiley;

    private JMenu gameMenu;
    private JMenuItem mItemBeginner;
    private JMenuItem mItemIntermediate;
    private JMenuItem mItemExpert;

    // TODO add timer
    // TODO add score or unmarked mine count
    HeaderPanel()
    {
        // setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        // setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        setLayout(new BorderLayout());
        setMAdapter();
        setMenuNewGameHandler();
        setMenuDifficultyHandler();

        icon = new ButtonIcon();
        smiley = new JButton(icon.SMILEY_DEFAULT);

        smiley.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        smiley.addMouseListener(mAdapter);

        JMenuBar menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");

        JMenuItem mItemNew = new JMenuItem(Command.NEW.getLabel());
        mItemBeginner = new JMenuItem(Command.BEGINNER.getLabel());
        mItemIntermediate = new JMenuItem(Command.INTERMEDIATE.getLabel());
        mItemExpert = new JMenuItem(Command.EXPERT.getLabel());
        JMenuItem mItemExit = new JMenuItem(Command.EXIT.getLabel());

        mItemNew.addActionListener(menuNewGameHandler);
        mItemBeginner.addActionListener(menuDifficultyHandler);
        mItemIntermediate.addActionListener(menuDifficultyHandler);
        mItemExpert.addActionListener(menuDifficultyHandler);
        mItemExit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

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

        setMenuItemStyle(gameMenu);
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

        setMenuDifficultyIcon(Command.BEGINNER);
    }

    // TODO improve setMenuItemIcon efficiency
    public void setPanelController(PanelController panelController)
    {
        this.panelController = panelController;
    }

    public void setSmiley(ImageIcon icon)
    {
        previousSmiley = (ImageIcon) smiley.getIcon();
        smiley.setIcon(icon);
    }

    // void setControllable(Controllable controllable)
    // {
    //     this.controllable = controllable;
    // }

    public void setMenuDifficultyIcon(Command difficulty)
    {
        for (Component menuComponent : gameMenu.getMenuComponents())
        {
            if (menuComponent instanceof JMenuItem)
            {
                ((JMenuItem) menuComponent).setIcon(null);
            }
        }

        switch (difficulty)
        {
            case BEGINNER:
                mItemBeginner.setIcon(icon.CHECK);
                // mItemIntermediate.setIcon(null);
                // mItemExpert.setIcon(null);
                break;
            case INTERMEDIATE:
                // mItemBeginner.setIcon(null);
                mItemIntermediate.setIcon(icon.CHECK);
                // mItemExpert.setIcon(null);
                break;
            case EXPERT:
                mItemBeginner.setIcon(null);
                mItemIntermediate.setIcon(null);
                mItemExpert.setIcon(icon.CHECK);
                break;
        }
    }

    private void setMAdapter()
    {
        mAdapter = new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    setSmiley(icon.SMILEY_PRESSED);
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
                        setSmiley(icon.SMILEY_DEFAULT);
                        // controllable.controlEvent(new CommandEvent(Command.NEW));
                        panelController.newGame();
                    }
                    else
                    {
                        Logger.debug("Mouse released outside of smiley button");
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    Logger.debug("Mouse exited while smiley was pressed");
                    setSmiley(previousSmiley);
                }
            }
        };
    }

    private void setMenuNewGameHandler()
    {
        menuNewGameHandler = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Logger.debug(e.getActionCommand());
                panelController.newGame();
            }
        };
    }

    private void setMenuDifficultyHandler()
    {
        menuDifficultyHandler = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Logger.debug(e.getActionCommand());
                Command selection = Command.getCommand(e.getActionCommand());

                panelController.newGame(selection);
                setSmiley(icon.SMILEY_DEFAULT);
            }
        };
    }

    private void setMenuItemStyle(JMenu menu)
    {
        for (Component menuComponent : menu.getMenuComponents())
        {
            if (menuComponent instanceof JMenuItem)
            {
                // ((JMenuItem) menuComponent).addActionListener(this);
                ((JMenuItem) menuComponent).setHorizontalTextPosition(SwingConstants.LEFT);
            }
        }
    }

    // @Override
    // public void actionPerformed(ActionEvent e)
    // {
    //     Logger.debug(e.getActionCommand());
    //     Command selection = Command.getCommand(e.getActionCommand());
    //
    //     controllable.controlEvent(new CommandEvent(selection));
    // }
}
