package Games;

import java.awt.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public interface AbstractGameFactory {
    Game createGame(AbstractGameFactory abf);
    Dimension getDimension();
   // GameBoard createGameBoard();
    void loadImages(Player goesFirst, Player two);
    void setInitOwnership( Player client, Player opponent);
}