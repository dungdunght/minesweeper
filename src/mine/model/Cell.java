/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author NguyenDinhDung
 */

public class Cell {
     // позиция
    private Point _position;
    private Mine _mine;
    private Flag _flag;
    private boolean _isOpened;
    //private GameField _myField;
    private GameModel _myModel;
    
   
    
// --------------------- Позиция  -----------------------
    /**
     * Конструктор создать ячейку с позицию pos
     * @param pos позиция ячейки
     * @param myField текущая поля 
     */
    public Cell(Point pos,GameModel myModel){
        _position=pos;
        _mine=new Mine(this);
        _flag=new Flag(this);
        _isOpened=false;
        _myModel=myModel;
        //_myField=myField;
        
    }
    
    /**
     * вернуть позицию у ячейки
     * @return позиция
     */
    public Point position(){
        return _position;
    }
    /**
     * вернуть радар у ячейки
     * @return радар у ячейки
     */


   
    /**
     * открыть ли ячейка  
     * @return true если уже открыли
     */
    public boolean isOpened(){
        return _isOpened;
    
    }
    /**
     * открыть ячейку
     */
    public void openCell(){
        _isOpened=true;
    }
    /**
     * закрыть ячейку
     */
    public void closeCell(){
        _isOpened=false;
    }
    /**
     * вернуть количество мины в соседних клетках
     * @return колчичество мины
     */
    public int getNumberNeighborMines(){
        
         int numberMine=_mine.numberNeiborMines();
         if (numberMine==-1){
             _mine.radarMineUpdate();
             numberMine=_mine.numberNeiborMines();
         }
         return numberMine;
    }
    
    /**
     * вернуть значение мины
     * @return значение мины
     */
    public Mine getMine(){
        return _mine;
    }
    
    public Flag getFlag(){
        return _flag;
    }    
    
     /**
     * получить соседние ячейки от ячейки currentCell
     * @param currentCell текущая ячейка
     * @return контейнер соседней ячеек от ячейки currentCell
     */
    public ArrayList<Cell> takeNeighborCell(){
        ArrayList<Cell> listNeighborCell=new ArrayList<Cell>();
        Point pos=this.position();
        int currentWidth=pos.x;
        int currentHeight=pos.y;
        int newWidth,newHeight;
        for (int i=-1;i<2;i++)
            for (int j=-1;j<2;j++)
            if (!(i==0&&j==0))
            {
                newWidth=currentWidth+i;
                newHeight=currentHeight+j;
                
                if (newWidth>0&&newWidth<=_myModel.width()&&newHeight>0&&newHeight<=_myModel.height()){
                    Cell newCell=_myModel.getCell(newWidth,newHeight);
                    listNeighborCell.add(newCell);
                }
            }
        return listNeighborCell;
      }
   
    /**
     * Открыть свободную пространству от ячейки cell
     * @param openedCell список открывающей ячейки 
     */
    public void recursiveFreeCells(ArrayList<Cell> openedCell){
        this.openCell();
            openedCell.add(this);
        int numberNeighborMine=this.getNumberNeighborMines();
        _myModel.fireCellNotMine(this.position(),numberNeighborMine);
        if (numberNeighborMine==0)
        {
            
                    ArrayList<Cell> listNeighborCell=this.takeNeighborCell();
                    for (int i=0;i<listNeighborCell.size();i++){
                        Cell newCell=listNeighborCell.get(i);
                        if (!openedCell.contains(newCell)){
                            if (!newCell.getMine().isMined()&&!newCell.getFlag().checkFlag()){
                                numberNeighborMine=newCell.getNumberNeighborMines();
                                    if (numberNeighborMine!=0){
                                    newCell.openCell();
                                    openedCell.add(newCell);
                                    _myModel.fireCellNotMine(newCell.position(),numberNeighborMine);
                                }

                                else
                                    recursiveFreeCells(openedCell);

                            }
                        }
                    }
        }
        

    
    }    
}
