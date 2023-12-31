/**
 * Author: Jakob Kvejborg
 * 2023
 * note that a lot of this code is static (like run() KeyListener class etc.)
 * alt + 9 = github history
 *
 * @Version 18/12/2023
 * @Since 26/11/2023
 */

// 18/12 adding objects

package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();


    }
}
