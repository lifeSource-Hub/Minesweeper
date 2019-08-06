package minesweeper.view;

import minesweeper.controller.PanelController;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame
{
    private HeaderPanel headerPnl;
    private GridPanel gridPnl;
    private PanelController panelController;

    public AppFrame()
    {
        this("Minesweeper");
    }

    public AppFrame(String title)
    {
        headerPnl = new HeaderPanel();
        gridPnl = new GridPanel();

        panelController = new PanelController();
        panelController.setHeaderPnl(headerPnl);
        panelController.setGridPnl(gridPnl);

        gridPnl.setPanelController(panelController);
        headerPnl.setPanelController(panelController);

        add(headerPnl, BorderLayout.NORTH);
        add(gridPnl, BorderLayout.CENTER);

        pack();
        setTitle(title);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setIconImage(new ButtonIcon().FRAME_ICON);
    }
}
