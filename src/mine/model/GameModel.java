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
import mine.events.FlagActionEvent;
import mine.events.FlagActionListener;

/**
 *
 * @author NguyenDinhDung
 */
public class GameModel {
    /**
/* Aбстракция всей игры; генерирует стартовую обстановку; поочередно передает 
* ход игрокам; следит за игроками с 
* целью определения конца игры
*/
    private int _width;
    private int _height;
    
    Cell[][] listCells;
    //private GameField _field;
    private Divercant stragedy;
    private int _numberFlag;
    private int _mines;
    private boolean loseGame;
    
   
    
// -------------------------------- Поле -------------------------------------

//    public GameField field(){
//        return _field;
//    }
    
    public Divercant divercant(){
        return stragedy;
    }
    

    /**
     * Конструктор GameModel с полей width*height  
     * @param width ширина
     * @param height высота
     * @param mines количество мины
     */
    public GameModel(int width,int height,int mines ){
         _width=width;
         _height=height;
        listCells=new Cell[width+1][height+1];
        
        for (int i=1;i<=_width;i++)
            for (int j=1;j<=_height;j++)
                listCells[i][j]=new Cell(new Point(i,j),this);
         
        //_field = new GameField(width,height);
        _mines=mines;
        _numberFlag=mines;
        stragedy=new StragedyRandom(this);
        loseGame=false;
       
    }
    public int width() {
        return _width;
    }

    public int height() {
        return _height;
    }
     
    public Cell getCell(int width,int height){
        return listCells[width][height];
    }
    
// ---------------------- Порождение обстановки на поле ---------------------
    

    /**
     * Создать полу
     */
//    public void generateField(){
//        _field.clear();
//        for(int row = 1; row <= field().height(); row++)
//        {
//            for(int col = 1; col <= field().width(); col++)
//            {
//                _field.createCell(new Point(col, row));
//            }
//        }
//        
//        stragedy.initiateMines(_mines);
//    }

// ----------------------------- Игровой процесс ----------------------------
    
    /**
     * Разминировать и обработать процесс диверсанта от текущей позиции
     * @param pos текущая позиция
     */

 
    public void MineClearingAndDivercantWork(Point pos){
       
        Cell currentCell=listCells[pos.x][pos.y];
        Mine mineOfCell=currentCell.getMine();        
        if (mineOfCell.isMined()){
            loseGame=true;
            stragedy.showMine();
        }
        else
        {   
            ArrayList<Cell> openedCell = new ArrayList<>();
            openedCell.clear();
            currentCell.recursiveFreeCells(openedCell);
        }
        
        if (!loseGame) stragedy.changeMinePosition();
        
    }
    /**
     * проверять победил ли игрок
     * @return true если игрок победил
     */
    public boolean checkWinGame(){
        int numberCells=_width*_height;
        int numberOpenCells=0;
        for (int i=1;i<=_width;i++)
            for (int j=1;j<=_height;j++)
                   if (listCells[i][j].isOpened())
                      numberOpenCells++; 
        return (numberOpenCells+_mines==numberCells);
    }
    
    /**
     * вернуть количество флаги
     * @return количество флаги
     */
    public int getNumberFlag(){
        return _numberFlag;
    }
    /**
     * Поставить или убрать флаг в текущей позицию
     * @param pos текущая позиция 
     */
   public void FlagTo(Point pos){
        Cell currentCell=listCells[pos.x][pos.y];
        if (!currentCell.isOpened())
        if (!currentCell.getFlag().checkFlag())
        {
            if (_numberFlag>0){
              currentCell.getFlag().setFlag();
              _numberFlag--;
              fireSetFlagToDislay(pos,_numberFlag);
          }
        }
        else{
              currentCell.getFlag().unsetFlag();
              _numberFlag++;
              fireUnSetFlagToDislay(pos,_numberFlag);
              
        }
            
            
   }
   
   public boolean checkLoseGame(){
       return loseGame;
   }

    //// --------------------- Порождает события, связанные с игроками -------------
    
    // Список слушателей
    private ArrayList FlagActionListnerList = new ArrayList(); 
 
    // Присоединяет слушателя
    public void addFlagActionListner(FlagActionListener l) { 
        FlagActionListnerList.add(l); 
    }
    
    // Отсоединяет слушателя
    public void removeFlagActionListner(FlagActionListener l) { 
        FlagActionListnerList.remove(l); 
    } 
    
    // Оповещает слушателей о событии
    protected void fireSetFlagToDislay(Point pos,int numberFlag) {
        
        FlagActionEvent e=new FlagActionEvent(this);
        e.setPos(pos);
        e.setNumberFlag(numberFlag);
        for (Object listner : FlagActionListnerList)
        {
            ((FlagActionListener)listner).setFlagToDislay(e);
        }
    }     
    
      protected void fireUnSetFlagToDislay(Point pos,int numberFlag) {
        
        FlagActionEvent e=new FlagActionEvent(this);
        e.setPos(pos);
        e.setNumberFlag(numberFlag);
        
        for (Object listner : FlagActionListnerList)
        {
            ((FlagActionListener)listner).unsetFlagToDislay(e);
        }
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

   
   
   
   
   
    

