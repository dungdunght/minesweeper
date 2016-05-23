/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import javax.swing.JFrame;
import mine.views.GamePanel;

/**
 *
 * @author NguyenDinhDung
 */
public class Minesweeper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        JFrame app = new GamePanel();
        app.setVisible(true);
    }
    
}
