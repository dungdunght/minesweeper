/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import mine.events.LoseGameEvent;
import mine.events.LoseGameListener;
import mine.events.ChangeCellEvent;
import mine.events.ChangeCellListener;

/**
 *
 * @author NguyenDinhDung
 */
public abstract class Divercant {
    
    protected ArrayList<Mine> _listMine;
    protected GameModel myModel;
    //protected GameField myfield;

    /**
     * конструктор создать диверсанту с полей _myfield
     * @param _model текущая модел
     */
    public Divercant(GameModel _model){
        //myfield=_myfield;
        myModel=_model;
        _listMine=new ArrayList<>();
    }
    /**
     * иницировать мины в поле с ограничного количества мины
     * @param mines количество мины
     */
    public void initiateMines(int mines){
        //int size=myModel.getNumberCell();
        
        int width=myModel.width();
        int height=myModel.height();
        boolean[][] checkCell= new boolean [width][height];
        
        for (int i=1;i<=width;i++)
            for(int j=1;j<=height;j++){
            checkCell[i][j]=false;
        }
        
        int randomMineX,randomMineY;
        Random rand = new Random(); 
        for (int i=0;i<mines;i++){
            do{
                randomMineX=rand.nextInt(width)+1;
                randomMineY=rand.nextInt(height)+1;
            }
            while(checkCell[randomMineX][randomMineY]);
            
            Cell cell=myModel.getCell(randomMineX,randomMineY);
            Mine mineOfCell=cell.getMine();
            mineOfCell.setMine();
            checkCell[randomMineX][randomMineY]=true;
            
            _listMine.add(cell.getMine());
                 
        }
        
        
        
    }
    /**
     * Процесс диверсанта, мины случайно перемещают от текущего места на другого 
     */
    abstract public void changeMinePosition();
    
    void showMine(){
        fireListMine(_listMine);
    }
    
    private ArrayList _LoseGameListnerList = new ArrayList();
    
    // Присоединяет слушателя
    public void addPlayerActionListener(LoseGameListener l) {
        _LoseGameListnerList.add(l);
    }
    
    // Отсоединяет слушателя
    public void removePlayerActionListener(LoseGameListener l) {
        _LoseGameListnerList.remove(l);
    } 
    
    // Оповещает слушателей о событии
    protected void fireListMine(ArrayList<Mine> l) {
        for (Object listener : _LoseGameListnerList)
        {
            LoseGameEvent e = new LoseGameEvent(this);
            e.setListPosMine(l);      
            ((LoseGameListener)listener).showMineToDislay(e);
        }        
    }
    
      
    private ArrayList _DivercantChangeCellListnerList = new ArrayList();
    
    // Присоединяет слушателя
    public void addDivercantChangeCellListner(ChangeCellListener l) {
        _DivercantChangeCellListnerList.add(l);
    }
    
    // Отсоединяет слушателя
    public void removeDivercantChangeCellListner(ChangeCellListener l) {
        _DivercantChangeCellListnerList.remove(l);
    } 
    
    // Оповещает слушателей о событии
    protected void fireCloseCell(Point pos) {
        for (Object listener : _DivercantChangeCellListnerList)
        {
            ChangeCellEvent e = new ChangeCellEvent(this);
            e.setPos(pos);      
            ((ChangeCellListener)listener).CloseCellInDislay(e);
        }        
    }
    
    protected void fireUpdateNeighborMineCell(Point pos, int numberNeighborMines) {
        for (Object listener : _DivercantChangeCellListnerList)
        {
            ChangeCellEvent e = new ChangeCellEvent(this);
            e.setPos(pos);      
            e.setNumberNeighborMine(numberNeighborMines);
            ((ChangeCellListener)listener).UpdateNumberNeighborMineInDislay(e);
        }        
    }
  
    
}
