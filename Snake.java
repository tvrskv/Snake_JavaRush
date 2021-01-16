package com.javarush.games.snake;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

import java.util.*;


public class Snake {
    public boolean isAlive = true;

    //В классе Snake должно существовать приватное поле Direction direction, инициализированное при объявлении значением Direction.LEFT.
    private Direction direction = Direction.LEFT;

    // В классе Snake должен существовать публичный сеттер поля direction — setDirection(Direction), который устанавливает полю класса значение, полученное в качестве параметра.
    public void setDirection(Direction direction) {
        if (!(((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) &&
                (snakeParts.get(0).x == snakeParts.get(1).x)) ||
                ((this.direction == Direction.UP || this.direction == Direction.DOWN) &&
                        (snakeParts.get(0).y == snakeParts.get(1).y))))
            this.direction = direction;
    }

    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake(int x, int y) {
        //В конструкторе должны быть созданы три объекта типа GameObject
        GameObject first = new GameObject(x, y);
        GameObject second = new GameObject(x + 1, y);
        GameObject third = new GameObject(x + 2, y);

        //Созданные в конструкторе объекты типа GameObject должны быть добавлены в список snakeParts
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            if (i == 0) {
                game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            } else {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            }
        }
    }

    public void move(Apple apple) {
        GameObject head = createNewHead(); // create new head
        if (checkCollision(head)) { // проверка на поедание самого себя
            isAlive = false;
            return;
        }
        if (head.x >= SnakeGame.WIDTH || head.x < 0 || head.y >= SnakeGame.HEIGHT || head.y < 0) { // проверяем новая голова в игоровом поле?
            isAlive = false;
        }
        if (head.x == apple.x && head.y == apple.y) { // проверка новая голова там где яблоко?
            apple.isAlive = false;

            snakeParts.add(0, head);
        } else {
            snakeParts.add(0, head);
            removeTail();
        }
    }

    public GameObject createNewHead() {
        GameObject gameObject = null;
        if(direction == Direction.UP)
            gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        if(direction == Direction.DOWN)
            gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        if(direction == Direction.RIGHT)
            gameObject = new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
        if(direction == Direction.LEFT)
            gameObject = new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y );
        return gameObject;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject object) {
        boolean answer = false;
        for (GameObject obj : snakeParts) {
            if (object.x == obj.x && object.y == obj.y) {
                answer = true;
                break;
            }
        }
        return answer;
    }

    public int getLength() {
            return snakeParts.size();
        }

}


