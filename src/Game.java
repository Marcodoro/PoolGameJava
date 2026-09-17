import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import java.security.Key;
import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batch;

    private Square playerSquare;
    private float inputWaitTimerGoodName;
    private ArrayList<Circle> squareCircles;
    private Circle playerBall;
    private ArrayList<Circle> testBalls;
    private float speed = 350f;
    private boolean isAiming = false;

    private float height = 720f;
    private float width = 1280f;

    private int rows = 12;
    private int cols = 16;
    private Boolean isGameGoing;
    private Square buttons;

    int[][] levelGrid = new int[rows][cols];

    @Override
    public void create() {
        start();
    }

    @Override
    public void render() {
        game();
    }

    public void game() {


            float deltaTime = Gdx.graphics.getDeltaTime();


            //System.out.println(Gdx.input.getX());

        batch.begin();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);






            //draw things here
            //System.out.println(enemySquare.x);




            playerBall.update(deltaTime);

            shapeRenderer.begin(ShapeType.Filled);





            //Important functions i guess
            handleInput(playerBall);
            collisionFromWalls(playerBall);



            playerBall.dy *= (float) 0.998f;
            playerBall.dx *= (float) 0.998f;

            float stopThreshold = 60.0f; //to stop the ball from rolling on forever a a super slow speed

            for (int i = 0; i < squareCircles.size(); i++) {

                Circle ballB = squareCircles.get(i);

                ballB.update(deltaTime);


                collisionFromWalls(ballB);

                float mouseY = height - Gdx.input.getY();

                boolean debug = true;


                for (int j = i + 1; j < testBalls.size(); j++) {

                    ballsInTheHole(playerBall, ballB);

                }
                ballB.draw(shapeRenderer);
            }
            //to be honest i have no idea what this does but i guess if it works dont touch it
            for (int i = 0; i < testBalls.size(); i++) {
                Circle ballA = testBalls.get(i);

                ballA.update(deltaTime);

                handleCollisionBalls(playerBall, ballA);

                collisionFromWalls(ballA);

                float mouseY = height - Gdx.input.getY();

                boolean debug = false;

                //prepare for the most brutal if statement ever
                //!! ITS VERY IMPORTANT DO NOT TOUCH THIS EVER FOR ANY REASON !!
                // !! EVEN THE SLIGHTEST CHANGE WILL BREAK EVERYTHING !!
                for (int j = i + 1; j < testBalls.size(); j++) {
                    Circle ballB = testBalls.get(j);
                    if ((Gdx.input.getX() >= ballA.x - ballA.size && Gdx.input.getX() <= ballA.x + ballA.size) && ((height - Gdx.input.getY()) >= ballA.y - ballA.size && (height - Gdx.input.getY()) <= ballA.y + ballA.size) && (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && (ballA.dx == 0 && ballA.dy == 0) && debug == true)) {
                        ballA.y = mouseY;
                        ballA.x = Gdx.input.getX();
                    }
                    if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
                        System.out.println(ballA.x + "Here" + ballA.y);
                    }
                    handleCollisionBalls(ballA, ballB);
                }



                ballA.dy *= 0.998f;
                ballA.dx *= 0.998f;

                if (Math.abs(ballA.dx) < stopThreshold && Math.abs(ballA.dy) < stopThreshold) {
                    ballA.dx *= 0.97f;
                    ballA.dy *= 0.97f;
                    if (Math.abs(ballA.dx) < 1f && Math.abs(ballA.dy) < 1f) {
                        ballA.dy = 0;
                        ballA.dx = 0;
                    }
                }

                ballA.draw(shapeRenderer);
            }



            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                playerBall.dx = (float) Math.random() * 1150f;
                playerBall.dy = (float) Math.random() * 1150f;
            }

            if (Math.abs(playerBall.dx) < stopThreshold && Math.abs(playerBall.dy) < stopThreshold) {
                playerBall.dx *= (float) 0.97f;
                playerBall.dy *= (float) 0.97f;
                if (Math.abs(playerBall.dx) < 1f && Math.abs(playerBall.dy) < 1f) {
                    playerBall.dy = 0;
                    playerBall.dx = 0;
                }
            }

            playerBall.draw(shapeRenderer);

            shapeRenderer.end();

            batch.end();

    }
    public void menu() {
        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        shapeRenderer.begin(ShapeType.Filled);
        //error shape renderer is null fix someday
        buttons = new Square((float) width / 2f, (float) height / 2f, 200f, 0f, 0f, 0f);
        buttons.draw(shapeRenderer);
        float mouseY = (float) height - Gdx.input.getY();
        if ((float) Gdx.input.getX() >= buttons.x && (float) Gdx.input.getX() <= buttons.x + buttons.size &&
            (float) mouseY >= buttons.y && (float) mouseY <= buttons.y + buttons.size) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                //good
            }
        }

        shapeRenderer.end();
        batch.end();
    }

    public void start() {
        System.out.println(levelGrid);
        batch = new SpriteBatch();


        shapeRenderer = new ShapeRenderer();



        playerBall = new Circle((float) width / 2, (float) height / 2, 0f, 0f, 50f, Color.RED);
        handleInput(playerBall);

        testBalls = new ArrayList<>();
        for (int c = 0; c < 5; c++) {
            Circle newBall = new Circle((float) 100f + (c * 100f), 100f + (c * 100f), 0f, 0f, 50f, Color.GREEN);
            testBalls.add(newBall);
        }

        squareCircles = new ArrayList<>();
        float offset = 50f;

        for (int b = 0; b < 4; b++) {

            float spawnX = offset + (b % 2) * (width - (offset * 2));

            float spawnY = offset + (b / 2) * (height - (offset * 2));

            Circle cornerBalls = new Circle(spawnX, spawnY, 0f, 0f, 50f, Color.YELLOW);
            squareCircles.add(cornerBalls);
        }

    }

    public void randomSpeed(Circle ballA, Circle ballB) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            ballA.dx = (float) Math.random() * 1150f;
            ballA.dy = (float) Math.random() * 1150f;
            ballB.dx = (float) Math.random() * 1150f;
            ballB.dy = (float) Math.random() * 1150f;
        }
    }
    //too tired lets do this tomorrow check if balls are in the holes
    //if yes then the balls should quickly become smaller until they are
    //so small and then just remove them from the array
    public void ballsInTheHole(Circle ball, Circle holes) {
        //System.out.println(holes.x + "f" + holes.y);
        if ((playerBall.x <= holes.x + holes.size) && (playerBall.x >= holes.x - holes.size) && (playerBall.y <= holes.y + holes.size) && (playerBall.y >= holes.y - holes.size)) {
            //Something happens
        }
    }

    public void collisionFromWalls(Circle Ball) {
        if (Ball.x <= Ball.size || Ball.x >= width - Ball.size) {
            Ball.dx = (float) Ball.dx * -1f;
        }
        if (Ball.y <= Ball.size ||Ball.y >= height - Ball.size) {
            Ball.dy = (float) Ball.dy * -1f;
        }
    }

    public void handleInput(Circle playerBall) {

        Vector2 position = new Vector2( (float) playerBall.x, (float) playerBall.y);
        //The coordinates mismatch between the renderer and mouse input is some bullshit this took me way too long to figure out!
        Vector2 mousePos = new Vector2 ((float) Gdx.input.getX(), (float) height - Gdx.input.getY());

        float distancex = (float) Gdx.input.getX() - playerBall.x;
        float distancey = (height - (float) Gdx.input.getY()) - playerBall.y;
        float mouseX = Gdx.input.getX();
        float mouseY = height - Gdx.input.getY();

        //lets stop zhe ball first
        if (playerBall.dy == 0 && playerBall.dx == 0){
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

                if ((mouseX > playerBall.x - playerBall.size) && (mouseX < playerBall.x + playerBall.size) &&
                    (mouseY > playerBall.y - playerBall.size) && (mouseY < playerBall.y + playerBall.size))
                {


                    isAiming = true;

                    float distanceOverall = distancey + distancex;
                }
                if (isAiming == true) {
                    shapeRenderer.setColor(Color.BLUE);
                    shapeRenderer.rectLine(position, mousePos, 5f);
                }

            } else if (isAiming == true) {

                distancex = distancex * -1;
                distancey = distancey * -1;
                playerBall.dx = distancex * 2;
                playerBall.dy = distancey * 2;

                isAiming = false;
            }
        }
    }

    public void handleCollisionBalls(Circle playerBall, Circle testBall) {

        float diffX = playerBall.x - testBall.x;
        float diffY = playerBall.y - testBall.y;
        float distance = (float) Math.sqrt((diffX * diffX) + (diffY * diffY));

        // if they are touchigng
        if (distance <= playerBall.size + testBall.size) {


            //  The overlapping bs
            float overlap = (playerBall.size + testBall.size) - distance;
            float pushAmount = (overlap / 2f) * 1.05f;

            float dirX = diffX / distance;
            float dirY = diffY / distance;

            playerBall.x += dirX * pushAmount;
            playerBall.y += dirY * pushAmount;

            testBall.x -= dirX * pushAmount;
            testBall.y -= dirY * pushAmount;

            float relativeVelocityX = playerBall.dx - testBall.dx;
            float relativeVelocityY = playerBall.dy - testBall.dy;
            float velocityAlongNormal = (relativeVelocityX * dirX) + (relativeVelocityY * dirY);

            if (velocityAlongNormal < 0) {

                float friction = 0.95f;
                float tanX = -dirY;
                float tanY = dirX;

                float dotPlayerNormal = (playerBall.dx * dirX) + (playerBall.dy * dirY);
                float dotTestNormal = (testBall.dx * dirX) + (testBall.dy * dirY);

                float dotPlayerTangent = ((playerBall.dx * tanX) + (playerBall.dy * tanY)) * friction;
                float dotTestTangent = ((testBall.dx * tanX) + (testBall.dy * tanY)) * friction;

                float tempNormal = dotPlayerNormal;
                dotPlayerNormal = dotTestNormal;
                dotTestNormal = tempNormal;

                playerBall.dx = (dotPlayerNormal * dirX) + (dotPlayerTangent * tanX);
                playerBall.dy = (dotPlayerNormal * dirY) + (dotPlayerTangent * tanY);

                testBall.dx = (dotTestNormal * dirX) + (dotTestTangent * tanX);
                testBall.dy = (dotTestNormal * dirY) + (dotTestTangent * tanY);

            }
        }
    }


}
