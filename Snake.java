package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;
public class Snake extends GameObject {


    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }

        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        }
        this.direction = direction;
    }

    private Direction direction = Direction.LEFT;
    public boolean isAlive = true;
    private final static String HEAD_SIGN = "\uD83D\uDC7E";
    private final static String BODY_SIGN = "\u26AB";

    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake(int x, int y) {
        super(x, y);

        GameObject one = new GameObject(x, y);
        GameObject two = new GameObject(x + 1, y);
        GameObject three = new GameObject(x + 2, y);
        snakeParts.add(one);
        snakeParts.add(two);
        snakeParts.add(three);


    }

    public void draw(Game game) {
        Color color = isAlive ? Color.BLACK : Color.RED;

        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            if (i == 0) {
                game.setCellValueEx(part.x, part.y, Color.NONE, HEAD_SIGN, color, 75);
            } else {
                game.setCellValueEx(part.x, part.y, Color.NONE, BODY_SIGN, color, 75);
            }
        }
    }

    public void move(Apple apple) {

        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH || newHead.x < 0 || newHead.y >= SnakeGame.HEIGHT || newHead.y < 0) {
            isAlive = false;
            return;
        }
       if( checkCollision(newHead)){
           isAlive = false;
           return;
       }
        snakeParts.add(0, newHead);

        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }

    }

    public GameObject createNewHead() {
        GameObject oldhead = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            return new GameObject(oldhead.x - 1, oldhead.y);
        } else if (direction == Direction.RIGHT) {
            return new GameObject(oldhead.x + 1, oldhead.y);
        } else if (direction == Direction.DOWN) {
            return new GameObject(oldhead.x, oldhead.y + 1);
        } else {
            return new GameObject(oldhead.x, oldhead.y - 1);
        }

    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject part : snakeParts) {
            if (part.x == gameObject.x && part.y == gameObject.y) {
                return true;


            }

        }
        return false;
    }
    public int getLength(){
        return snakeParts.size();
    }
}
