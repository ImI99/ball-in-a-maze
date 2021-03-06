package Model;

import lombok.Getter;
import java.awt.Point;

/***
 * Class that represents a gamestate.
 */
public class Game {

    /***
     * Directions in which the ball can be moved
     */
    public enum DIRECTION{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    @Getter
    private Board board;

    @Getter
    private int score;

    @Getter
    private Point ballPosition;

    public Game(int size, Point start, Point goal){
        this.board = new Board(size,goal);
        this.ballPosition = start;
    }

    /***
     * Sets the goal to the given coordinates
     * @param x the x value of the coordinate
     * @param y the y value of the coordinate
     * @throws IllegalArgumentException thrown when the given coordinates are invalid
     */
    public void setGoal(int x, int y){
        if(x < this.board.getSize() && y < this.board.getSize() && x >= 0 && y >= 0){
            this.getBoard().setGoal(new Point(x,y));
        }else {
            throw new IllegalArgumentException("Invalid ball position");
        }
    }
    /***
     * Sets the current position of the ball to the given coordinates
     * @param x the x value of the coordinate
     * @param y the y value of the coordinate
     * @throws IllegalArgumentException thrown when the given coordinates are invalid
     */
    public void setBallPosition(int x, int y) throws IllegalArgumentException {
        if(x < this.board.getSize() && y < this.board.getSize() && x >= 0 && y >= 0){
            this.ballPosition = new Point(x, y);
        }else {
            throw new IllegalArgumentException("Invalid ball position");
        }
    }


    /***
     * Sets up a wall between the given tiles.
     * @param tile1 Coordinates of a tile given as <code>java.awt.Point</code>
     * @param tile2 Coordinates of a tile given as <code>java.awt.Point</code>
     * @throws IllegalArgumentException is thrown when the given coordinates are not valid
     */
    public void setWall(Point tile1, Point tile2){
        if(tile1.x >= board.getSize() || tile2.x >= board.getSize() || tile1.y >= board.getSize() || tile2.y >= board.getSize()){
            throw new IllegalArgumentException();
        }
        if(tile1.x == tile2.x){
            if ( tile2.y -1 == tile1.y ){
                this.board.getTile(tile1).setRight(true);
                this.board.getTile(tile2).setLeft(true);
            }
            else if( tile2.y +1 == tile1.y ){
                this.board.getTile(tile1).setLeft(true);
                this.board.getTile(tile2).setRight(true);
            }else{
                throw new IllegalArgumentException("Invalid tiles");
            }
        }else if(tile1.y == tile2.y){
            if ( tile2.x -1 == tile1.x ){
                this.board.getTile(tile2).setUp(true);
                this.board.getTile(tile1).setDown(true);
            }
            else if ( tile2.x +1 == tile1.x){
                this.board.getTile(tile2).setDown(true);
                this.board.getTile(tile1).setUp(true);
            }else{
                throw new IllegalArgumentException("Invalid tiles");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid tiles");
        }
    }

    /***
     * Moves the ball into the given direction until it hits a wall.
     * @param dir The direction of the movement
     * @return The new position of the ball
     */
    public Point MoveBall(DIRECTION dir){
        switch (dir) {
            case UP:
                while (!this.board.getTile(ballPosition).isWall(dir)){
                    ballPosition.x--;
                }
                break;
            case DOWN:
                while (!this.board.getTile(ballPosition).isWall(dir)){
                    ballPosition.x++;
                }
                break;
            case LEFT:
                while (!this.board.getTile(ballPosition).isWall(dir)){
                    ballPosition.y--;
                }
                break;
            case RIGHT:
                while (!this.board.getTile(ballPosition).isWall(dir)){
                    ballPosition.y++;
                }
                break;
        }
        this.score++;
        return this.ballPosition;
    }

    /***
     * Returns if a ball is in the goal position or not.
     * @return <code>True</code> if the ball is in a goal position, <code>False</code> if it is not
     */
    public boolean GoalReached(){
        return this.board.getTile(this.ballPosition).isGoal();
    }

    public Tile getTile(int x, int y){
        return this.board.getTile(new Point(x,y));
    }
}
