/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.model;

import java.util.ArrayList;

/**
 *
 * @author NguyenDinhDung
 */
public class Mine extends AbstractObjects {
    /**
     * поставить ячеек в мине
     * @param cell ячеек
     */
    
    private boolean _isMined;
    private int numberMineNeibor;
    public Mine(Cell cell){
        _isMined=false;
        setCell(cell);
        numberMineNeibor=-1;
    }
     public void setMine(){
        _isMined=true;
    }
    
    public void unsetMine(){
        _isMined=false;
    }
    
    public boolean isMined(){
        return _isMined;
    } 
    public boolean radarMineUpdate(){
        
        ArrayList<Cell> listNeighborCell=_cell.takeNeighborCell(); 
        
        int newNumberMines=0;
        
        for (int i=0;i<listNeighborCell.size();i++){
               Cell newCell=listNeighborCell.get(i);
                    if (newCell.getMine().isMined())
                        newNumberMines++;
                }
                
         
         if (numberMineNeibor!=newNumberMines){
             numberMineNeibor=newNumberMines;
             return true;
         }
         return false;
    }
    public int numberNeiborMines(){
        return numberMineNeibor;
    }
        /**
     * востановить начальное состояние радара
     */
    public void unRadarMineUpdate(){
        numberMineNeibor=-1;
    }
    
}
