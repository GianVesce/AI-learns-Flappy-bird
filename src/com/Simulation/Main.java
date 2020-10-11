package com.Simulation;

import Game.RenderingPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    //The dimensions of the window (without the non-client area i.e. window's shadows or title bar
    public static final Dimension clientArea = new Dimension(1280, 720);

    public static void main(String[] args) {
        //The dimensions of the screen. In my case, i have a 1920x1080 screen
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //The window dimensions with the non-client area added
        final Dimension windowDimension = new Dimension(clientArea.width + 8 + 8, clientArea.height + 31 + 8);

        JFrame window = new JFrame("A.I. learns to play Flappy Bird");
        //window.setIconImage(new ImageIcon("flappy-bird-assets-master/favicon.ico").getImage()); TODO: Make this work

        window.setBounds(screenSize.width/2 -   windowDimension.width/2, screenSize.height/2 - windowDimension.height/2,
                                                   windowDimension.width,                            windowDimension.height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(null);

        RenderingPanel renderingPanel = new RenderingPanel();
        renderingPanel.setBounds(0, 0, clientArea.width, clientArea.height);
        renderingPanel.initialize();
        window.add(renderingPanel);

        window.setVisible(true);

        while (true) {
            //Update the game's engine and graphics
            renderingPanel.updateGameLoop();
        }
    }
}
