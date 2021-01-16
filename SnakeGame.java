package com.javarush.games.snake;
import com.javarush.engine.cell.*;


public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;

    private int turnDelay;

    private Apple apple;

    private boolean isGameStopped; // хранение состояния игры

    private static final int GOAL = 28; // конечный размер змейки

    private int score;



    public void initialize() {
        setScreenSize(15, 15);
        createGame();
    }

    /* Чтобы окрасить ячейки игрового поля в определенный цвет нужно пройти по всем ячейкам (используй циклы)
    и для каждой из них вызвать метод setCellColor.
     */
    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);

    }

    /* Создание игры
     */
    private void createGame() {
        // Apple apple = new Apple(5, 5);
        score = 0; // отображение очков

        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
        setScore(score);
        turnDelay = 300;  // временной промежуток на ход
        setTurnTimer(turnDelay);

    }

    @Override
    public void onTurn(int x) {
        snake.move(apple);
        if (apple.isAlive == false) {
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        if (snake.isAlive == false) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        if (!apple.isAlive) {

        }
        drawScene();
    }


    @Override
    public void onKeyPress(Key key) {

        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        }
        if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        }
        if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        }
        if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        }
        if (key == Key.SPACE && isGameStopped == true) { // htcnfhnbuhs
            createGame();
        }
    }

    private void createNewApple() {
        //apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        } while (snake.checkCollision(apple));
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.YELLOW, "GAME OVER", Color.GRAY, 75);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.YELLOW, "YOU WIN", Color.GRAY, 75);
    }
}