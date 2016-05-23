/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.events;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EventObject;
import mine.model.Mine;

/**
 *
 * @author NguyenDinhDung
 */
public class LoseGameEvent extends EventObject{
    
    private ArrayList<Point> _listPosMine=new ArrayList<>();
    
    public void setListPosMine(ArrayList<Mine> listMine){
        for (int i=0;i<listMine.size();i++)
        {
            _listPosMine.add(listMine.get(i).getCell().position());
        }
    }
    
    public ArrayList<Point> getListPosMine(){
        return _listPosMine;
    }
    
    public LoseGameEvent(Object source) { 
        super(source); 
    } 
}
