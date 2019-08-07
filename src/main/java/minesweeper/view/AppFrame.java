package minesweeper.view;

import minesweeper.controller.PanelController;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame
{
    public AppFrame()
    {
        this("Minesweeper");
    }

    public AppFrame(String title)
    {
        HeaderPanel headerPnl = new HeaderPanel();
        GridPanel gridPnl = new GridPanel();

        PanelController panelController = new PanelController();
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
