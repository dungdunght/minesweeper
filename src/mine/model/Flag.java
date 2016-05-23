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
public class Flag extends AbstractObjects {
    /**
     * поставить ячееку в флаге
     * @param cell ячеек
     */
    private boolean _isFlag;
    public Flag(Cell cell){
        _isFlag=false;
        setCell(cell);
    }
     /**
     * Поставить фалг
     */
    public void setFlag(){
        _isFlag=true;
    }
    /**
     * Убрать флаг
     */
    public void unsetFlag(){
        _isFlag=false;
    }
    /**
     * Есть ли в ячейке флаг
     * @return true если есть флаг
     */
    public boolean checkFlag(){
        return _isFlag;
    }
}
