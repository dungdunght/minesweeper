/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.model;

/**
 *
 * @author NguyenDinhDung
 */
public abstract class AbstractObjects {
    Cell _cell;
    
    /**
     * Поставить текуцую ячейку в _cell 
     * @param cell текущая ячейка
     */
    public void setCell(Cell cell){
        _cell=cell;
    }
    
    /**
     * вернуть текущую ячейку
     * @return текущая ячейка
     */
    public Cell  getCell(){
        return _cell;
    }
}
