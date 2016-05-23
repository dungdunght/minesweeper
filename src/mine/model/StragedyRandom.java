/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import mine.events.ChangeCellEvent;
import mine.events.ChangeCellListener;

/**
 *
 * @author NguyenDinhDung
 */
public class StragedyRandom extends Divercant{
    /**
     * Процесс диверсанта, мины случайно перемещают от текущего места на другого 
     */
    
    public StragedyRandom(GameField myfield){
        super(myfield);
    }   
     
    @Override
    public void changeMinePosition(){
        
        int numberCell=myfield.getNumberCell();
        int numberMine=_listMine.size();
        Random rand = new Random(); 
        Mine mineChange=_listMine.get(rand.nextInt(numberMine));
        
        _listMine.remove(mineChange);
        
        Cell cellOfMineChange=mineChange.getCell();
        cellOfMineChange.getMine().unsetMine();
        
        System.out.println("Min: "+Integer.toString(cellOfMineChange.position().y)+" "+Integer.toString(cellOfMineChange.position().x));
        
        Cell cellChange;
        do 
            cellChange=myfield.getCell(rand.nextInt(numberCell));
        while (cellChange.getMine().isMined());
        
         System.out.println("noi chuyen: "+Integer.toString(cellChange.position().y)+" "+Integer.toString(cellChange.position().x));
        
        cellChange.getMine().setMine();
        _listMine.add(cellChange.getMine());
         
        ArrayList<Cell> listNeighborCell=cellChange.takeNeighborCell();
        if (cellChange.isOpened())
        {

            cellChange.getMine().unRadarMineUpdate();
            cellChange.closeCell();
            fireCloseCell(cellChange.position());    
            for (int i=0;i<listNeighborCell.size();i++){

                Cell neighborCell=listNeighborCell.get(i);
                if (neighborCell.isOpened()){

                    neighborCell.closeCell();
                    neighborCell.getMine().unRadarMineUpdate();
                    //event
                    fireCloseCell(neighborCell.position());
                }

            }
                
            
        }
        else{
            for (int i=0;i<listNeighborCell.size();i++){

                Cell neighborCell=listNeighborCell.get(i);
                
                if (neighborCell.isOpened()&&neighborCell.getMine().radarMineUpdate()){
                    //event
                    fireUpdateNeighborMineCell(neighborCell.position(),neighborCell.getNumberNeighborMines());
                }
            }
           
        }
        listNeighborCell.clear();
        listNeighborCell=cellOfMineChange.takeNeighborCell();
        System.out.println(Integer.toString(cellOfMineChange.position().y)+" "+Integer.toString(cellOfMineChange.position().x));
        for (int i=0;i<listNeighborCell.size();i++){

                Cell neighborCell=listNeighborCell.get(i);
                if (neighborCell.isOpened()){
                    if (neighborCell.getMine().radarMineUpdate())
                    {
                        if (neighborCell.getNumberNeighborMines()!=0){
                            
                            fireUpdateNeighborMineCell(neighborCell.position(),neighborCell.getNumberNeighborMines());
                            System.out.println(Integer.toString(neighborCell.position().y)+" "+Integer.toString(neighborCell.position().x)+" "+Integer.toString(neighborCell.getNumberNeighborMines()));
                        }
                        else{
                            fireCloseCell(neighborCell.position());
                            neighborCell.getMine().unRadarMineUpdate();
                            ArrayList<Cell> openedCell = new ArrayList<>();
                            neighborCell.recursiveFreeCells(openedCell);
                        }
                            
                    }
                }
        }

      
    }
    

}
