package com.javarush.games.snake;

import com.javarush.engine.cell.*;
public class SnakeGame extends Game {

    private int score;
    private static final int GOAL=28;
    private boolean isGameStopped;
    private Apple apple;
    private int turnDelay;
    private  Snake snake;
    public static final int WIDTH=15;
    public static final int HEIGHT=15;
    @Override
    public void initialize(){
        setScreenSize( WIDTH,HEIGHT);
        createGame();
    }
    private void drawScene(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.BURLYWOOD,"");

            }

        }
        snake.draw(this);
        apple.draw(this);
    }
    private void createGame(){
        snake= new Snake(WIDTH/2,HEIGHT/2);
        createNewApple();
        isGameStopped=false;
        drawScene();
        turnDelay=300;
        setTurnTimer(turnDelay);
        score=0;
        setScore(score);
    }
    private void createNewApple(){
        
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            apple = new Apple(x, y);

        } while (snake.checkCollision(apple));





    }

    @Override
    public void onTurn(int step) {
    if(apple.isAlive==false){
        createNewApple();
        score+=5;
        setScore(score);
        turnDelay-=10;
        setTurnTimer(turnDelay);
    }
        snake.move(apple);
    if(snake.isAlive==false){
        gameOver();
    }
    if(snake.getLength()>GOAL){
        win();
    }

        drawScene();
    }

    @Override
    public void onKeyPress(Key x){
        if (x==Key.LEFT){
           snake.setDirection(Direction.LEFT);
        } else if (x==Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);

        } else if (x==Key.UP) {
            snake.setDirection(Direction.UP);
        }else if (x==Key.DOWN) {
            snake.setDirection(Direction.DOWN);

        }
        if(x==Key.SPACE && isGameStopped == true ){
            createGame();
        }
    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.BLACK,"you close",Color.RED,100);

    }
    private void win(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.BLACK,"You Win",Color.GOLD,100);
    }

}
