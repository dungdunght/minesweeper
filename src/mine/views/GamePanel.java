/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import mine.events.LoseGameEvent;
import mine.events.LoseGameListener;
import mine.events.FlagActionEvent;
import mine.events.FlagActionListener;
import mine.events.ChangeCellEvent;
import mine.events.ChangeCellListener;
import mine.model.GameModel;



/**
 *
 * @author NguyenDinhDung
 */
public class GamePanel extends JFrame{
    private JPanel fieldPanel = new JPanel();
    
    private JPanel infoPanel = new JPanel();
    private JLabel flagInfo = new JLabel();

    private JMenuBar menu = null;
    private final String fileItems[] = new String []{"New", "Exit"};
    
    private final int CELL_SIZE = 50;
    
   private GameModel _model; 
 //private GameModel _model = new GameModel();
     public GamePanel() {
        super();

        this.setTitle("Сапёр");
        
        createMenu();
        setJMenuBar(menu);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Box mainBox = Box.createVerticalBox();

        // Информационная панель
        mainBox.add(Box.createVerticalStrut(10));
        

        // Игровое поле
        mainBox.add(Box.createVerticalStrut(10));
        fieldPanel.setDoubleBuffered(true);

     
        mainBox.add(fieldPanel);
        
        Dimension fieldDimension = new Dimension(500, 500);
        
        fieldPanel.setPreferredSize(fieldDimension);
        fieldPanel.setMinimumSize(fieldDimension);
        fieldPanel.setMaximumSize(fieldDimension);

        setContentPane(mainBox);
        pack();
        setResizable(true);


    }
     
    // ---------------------- Создаем информационную панель -----------------------
    
    private Box createInfoPanel() {
        
        Box box = Box.createHorizontalBox();
        
        box.add(Box.createHorizontalStrut(10));
        
        box.add(new JLabel("Flag :"));
        flagInfo.setText(Integer.toString(_model.getNumberFlag()));
        box.add(Box.createHorizontalStrut(10));
        box.add(flagInfo);
        
        box.add(Box.createHorizontalStrut(20));

     
        
        return box;
    } 
  
     private void createField(){
        
        fieldPanel.setDoubleBuffered(true);
        fieldPanel.setLayout(new GridLayout(_model.field().height(), _model.field().width()));
        
        Dimension fieldDimension = new Dimension(CELL_SIZE* _model.field().width(), CELL_SIZE*_model.field().height());
        
        fieldPanel.setPreferredSize(fieldDimension);
        fieldPanel.setMinimumSize(fieldDimension);
        fieldPanel.setMaximumSize(fieldDimension);
        
        repaintField();
    }
//    
     public void repaintField() {
        
        fieldPanel.removeAll();

        for (int row = 1; row <= _model.field().height(); row++) 
        {
            for (int col = 1; col <= _model.field().width(); col++) 
            {
                JButton button;

                button = new JButton("");
                button.setFocusable(false);
                fieldPanel.add(button);
                button.addActionListener(new ClickListener());
                button.addMouseListener(new RightClickListener());
            }
        }

        fieldPanel.validate();
    }
    private Point buttonPosition(JButton btn){
        
        int index = 0;
        for(Component widget: fieldPanel.getComponents())
        {
            if(widget instanceof JButton)
            {
                if(btn.equals((JButton)widget))
                {   
                    break;
                }
                
                index++;
            }
         }
        
        int fieldWidth = _model.field().width();
        return new Point(index%fieldWidth + 1, index/fieldWidth + 1);
    }
        
   private JButton getButton(Point pos) {

       int index = _model.field().width()*(pos.y-1) + (pos.x-1);
       
        for(Component widget: fieldPanel.getComponents())
        {
            if(widget instanceof JButton)
            {
                if(index == 0)
                {
                    return (JButton)widget;
                }
                index--;
            }
         }
        
        return null;
    }
   
   private void setEnabledField(boolean on){

        Component comp[] = fieldPanel.getComponents();
        for(Component c : comp)
        {    c.setEnabled(on);   }
    }
   
   // ----------------------------- Создаем меню ----------------------------------  
    
    private void createMenu() {
 
        menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        for (int i = 0; i < fileItems.length; i++) {
           
            JMenuItem item = new JMenuItem(fileItems[i]);
            item.setActionCommand(fileItems[i].toLowerCase());
            item.addActionListener(new NewMenuListener());
            fileMenu.add(item);
        }
        fileMenu.insertSeparator(1);

        menu.add(fileMenu);
       
    }
        public class NewMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("exit".equals(command)) {
                System.exit(0);
            }
            if ("new".equals(command)) {
                 createGame();
                 
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Box mainBox = Box.createVerticalBox();

                // Информационная панель
                mainBox.add(Box.createVerticalStrut(10));
                mainBox.add(createInfoPanel());

                // Игровое поле
                mainBox.add(Box.createVerticalStrut(10));
                fieldPanel.setDoubleBuffered(true);
        
                createField();
                mainBox.add(fieldPanel);
        
                setContentPane(mainBox);
                pack();
                setResizable(false);
                 
                
                
     
            }  
        }
    }
    
    private void createGame(){
    
        // game field's size
        JSpinner widthBox = new JSpinner(new SpinnerNumberModel(5, 5, 10, 1));
        JSpinner heightBox = new JSpinner(new SpinnerNumberModel(5, 5, 10, 1));
        JSpinner mineBox = new JSpinner(new SpinnerNumberModel(5, 5, 10, 1));
        
        JPanel size= new JPanel();
        
        
        size.add(new JLabel("Width"));
        size.add(widthBox);
        
        size.add(Box.createHorizontalStrut(10));
        
        size.add(new JLabel("Height"));
        size.add(heightBox);
        
        size.add(new JLabel("Mines"));
        size.add(mineBox);

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(size);
        
        JOptionPane.showMessageDialog(null, mainPanel, "Game setting", JOptionPane.INFORMATION_MESSAGE);
        
        int _width=(int) widthBox.getValue();
        int _height=(int) heightBox.getValue();
        int _mines=(int) mineBox.getValue();
        
        _model=new GameModel(_width,_height,_mines);
        _model.generateField();
        _model.divercant().addPlayerActionListener(new LoseGameObserver());
        _model.field().addListnerOpenCellNotMineList(new changeCellObserver());
        
        _model.addFlagActionListner(new FlagActionObserver());
        _model.divercant().addDivercantChangeCellListner(new changeCellObserver());
        
      
        
        
    }
    
    
    // ------------------------- Реагируем на действия игрока ----------------------
    
    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           
            JButton button = (JButton) e.getSource();
            Point p = buttonPosition(button);
            if (!_model.field().getCell(p).getFlag().checkFlag()&&!_model.checkLoseGame()){
                
                button.setEnabled(false);
                _model.MineClearingAndDivercantWork(p);
                if (_model.checkWinGame()){
                    JOptionPane.showMessageDialog(null, "Ты выиграл ", "xxxxx!", JOptionPane.INFORMATION_MESSAGE);
                    setEnabledField(false);
                }
            }
       
            
            
        }
    }
    
   private class RightClickListener implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            
        }
    
       @Override
      public void mouseReleased(MouseEvent e) {
      }
      @Override
      public void mouseEntered(MouseEvent e) {
      }
      @Override
      public void mouseExited(MouseEvent e) {
      }
       @Override
      public void mouseClicked(MouseEvent e){
              if (e.getButton()== MouseEvent.BUTTON3){
               JButton button = (JButton) e.getSource();
               Point p = buttonPosition(button);
               
                   _model.FlagTo(p);
                   
            }
      }
      
   }
      
    private class LoseGameObserver implements LoseGameListener{

        @Override
        public void showMineToDislay(LoseGameEvent e) {
            
            ArrayList<Point> listMine=e.getListPosMine();
            JButton btn;
            for(int i=0;i<listMine.size();i++){
                btn=getButton(listMine.get(i));
                ImageIcon img = new ImageIcon("/Users/NguyenDinhDung/Desktop/minesweeper/mine.png");
                btn.setIcon(img);
                btn.setEnabled(false);
            }
            JOptionPane.showMessageDialog(null, "Ты проиграл ", "xxxxx!", JOptionPane.INFORMATION_MESSAGE);
            
            setEnabledField(false);
        }

     
            
        }
    

       private class FlagActionObserver implements FlagActionListener{

            @Override
            public void setFlagToDislay(FlagActionEvent e) {


                JButton btn=getButton(e.getPos());
                ImageIcon img = new ImageIcon("/Users/NguyenDinhDung/Desktop/minesweeper/flag.png");
                btn.setIcon(img);
                flagInfo.setText(Integer.toString(e.getNumberFlag()));
            }

            @Override
            public void unsetFlagToDislay(FlagActionEvent e) {


                JButton btn=getButton(e.getPos());
                btn.setIcon(null);
                flagInfo.setText(Integer.toString(e.getNumberFlag()));
            }
       }
       
      private class changeCellObserver implements ChangeCellListener{

            @Override
            public void CloseCellInDislay(ChangeCellEvent e) {

                JButton btn=getButton(e.getPos());
                btn.setText("");
                btn.setEnabled(true);
            }

            @Override
            public void UpdateNumberNeighborMineInDislay(ChangeCellEvent e) {


                JButton btn=getButton(e.getPos());
                btn.setText(Integer.toString(e.getNumberNeighborMine()));
                btn.setEnabled(false);
                
            }
            @Override
            public void showCellNotMineDislay(ChangeCellEvent e){
                JButton btn=getButton(e.getPos());
                int numberNeighborMine=e.getNumberNeighborMine();
                if (numberNeighborMine!=0) 
                    btn.setText(Integer.toString(numberNeighborMine));
                
                btn.setEnabled(false);
            }
       }
        
    
}
