/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mine.events;

import java.util.EventListener;

/**
 *
 * @author NguyenDinhDung
 */
public interface FlagActionListener extends EventListener{
    public void setFlagToDislay(FlagActionEvent e);
    public void unsetFlagToDislay(FlagActionEvent e);
}
