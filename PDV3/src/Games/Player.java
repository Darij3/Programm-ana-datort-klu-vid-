package Games;

import javax.swing.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/9/2017
 */
public class Player implements Serializable
{
	private static final long serialVersionUID = 269944423105793481L;
	boolean starts = false; //red starts
   // ArrayList<ImageIcon> playerPieces;
    
    public String name;

    Player(String name) {
       //playerPieces = new ArrayList<>(2);
        this.name = name;
        
    }

    public String getName() {
        return name;
    }

   
}