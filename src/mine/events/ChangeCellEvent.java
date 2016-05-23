/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.events;

import java.awt.Point;
import java.util.EventObject;

/**
 *
 * @author NguyenDinhDung
 */
public class ChangeCellEvent extends EventObject{
    Point _pos;
    int _numberNeighborMine;
    
    public void setPos(Point pos){
        _pos=pos;
    }
    
    public void setNumberNeighborMine(int numberNeighborMine){
        _numberNeighborMine=numberNeighborMine;
    }
    
    public Point getPos(){
        return _pos;
    }
    
    public int getNumberNeighborMine(){
        return _numberNeighborMine;
    }
    
    public ChangeCellEvent(Object source) { 
        super(source); 
    } 
}
