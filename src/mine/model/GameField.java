/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.model;

import java.awt.Point;
import java.util.ArrayList;
import mine.events.ChangeCellEvent;
import mine.events.ChangeCellListener;


/**
 *
 * @author NguyenDinhDung
 */
public class GameField {
    
  
   
   
    private ArrayList<Cell> _listCells = new ArrayList<>();
    //private boolean[][] cellRandom=new boolean[6][6] ;     
    
   
    

// ------------------------------ Ячейки ---------------------------------------    
    /**
     * Получить ячейку от его позиции
     * @param pos позиции в игре
     * @return ячейка этого позиции
     */
    public Cell getCell(Point pos){
        for (Cell c : _listCells){
            if (c.position().equals(pos)) return c; 
        }
        return null;
    }
    
    
        
    /**
     * создать новый ячейку сохранить позицию клетки
     * @param pos позиция клетки
     */
    public void createCell(Point pos)
    {
        Cell _cell=new Cell(pos,this);
        _listCells.add(_cell);
    }
    
    /**
     * получить количество ячеек
     * @return количество ячеек
     */
    public int getNumberCell(){
    
        return _listCells.size();
    }
    
    /**
     * получить количество ячеек, которые уже открыл
     * @return количество ячеек, которые уже открыл
     */
    public int getNumberOpenedCells(){
        int numberOpenedCells=0;
        for (Cell c : _listCells){
            if (c.isOpened()) numberOpenedCells++;
        }
        return numberOpenedCells;
    }
    
    /**
     * получить ячейку с позицией width*height  
     * @param width координат по х
     * @param height координат по у
     * @return ячейка с позицией width*height 
     */
    public Cell getCell(int width,int height){
        Point pos= new Point(width,height);
        return getCell(pos);
    }
    
    /**
     * получить ячейку с индексом index
     * @param index индекс ячейки 
     * @return ячейка с индексом index
     */
    public Cell getCell(int index){
        return _listCells.get(index);
    }
    
    
    
    
    
    /**
     * проверить открыли ли клетка с позицию pos
     * @param pos позиция клетки
     * @return true если уже открыл
     */
    public boolean checkOpened(Point pos){
        Cell currentCell=getCell(pos);
        return currentCell.isOpened();
    }
    
    
    /**
     * удалить список ячеек
     */
    public void clear(){
        _listCells.clear();
    }    
    
    
 
  
   

    
// ----------------------- Ширина и высота поля ------------------------------
    private int _width;
    private int _height;

   

    public int width() {
        return _width;
    }

    public int height() {
        return _height;
    }
    
// ----------------------------------------------------------------------------    
    /**
     * конструктор создать gameField с размер width*height
     * @param width ширина
     * @param height высота
     */
    public GameField(int width,int height) {
        
        _width=width;
        _height=height;
        //setSize(10, 10);
    }
    

    
    
    private ArrayList _listenerOpenCellNotMineList = new ArrayList(); 
    public void addListnerOpenCellNotMineList(ChangeCellListener l) { 
        _listenerOpenCellNotMineList.add(l); 
    }
    protected void fireCellNotMine(Point pos, int numberNeighborMine) { 
        ChangeCellEvent e = new ChangeCellEvent(this);
        e.setPos(pos);
        e.setNumberNeighborMine(numberNeighborMine);
        for (Object listner : _listenerOpenCellNotMineList)
        {
            ((ChangeCellListener)listner).showCellNotMineDislay(e);
        }
    } 
       
}
