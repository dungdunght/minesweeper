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
public class FlagActionEvent extends EventObject{
   private Point _pos;
   int _numberFlag;
   public void setPos(Point pos){
       _pos=pos;
   }
   public Point getPos(){
       return _pos;
   }
   public void setNumberFlag(int numberFlag){
       _numberFlag=numberFlag;
   }
   public int getNumberFlag(){
       return _numberFlag;
   }
    public FlagActionEvent(Object source) { 
        super(source); 
    } 
    
}
