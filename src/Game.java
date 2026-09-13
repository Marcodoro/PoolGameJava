import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;
import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batch;

    private Square playerSquare;
    private float inputWaitTimerGoodName;
    private Circle playerBall;
    private ArrayList<Circle> testBalls;
    private float speed = 350f;
    private boolean isAiming = false;

    private float height = 600f;
    private float width = 800f;

    private int rows = 12;
    private int cols = 16;


    int[][] levelGrid = new int[rows][cols];

    @Override
    public void create() {



        System.out.println(levelGrid);
        batch = new SpriteBatch();


        shapeRenderer = new ShapeRenderer();



        playerBall = new Circle((float) width / 2, (float) height / 2, 0f, 0f, 50f, Color.RED);

        testBalls = new ArrayList<>();
        for (int c = 0; c < 5; c++) {
            Circle newBall = new Circle((float) 100f + (c * 100f), 100f + (c * 100f), 0f, 0f, 50f, Color.GREEN);
            testBalls.add(newBall);
        }



    }

    @Override
    public void render() {

        float deltaTime = Gdx.graphics.getDeltaTime();


        //System.out.println(Gdx.input.getX());

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();





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


        //to be honest i have no idea what this does but i guess if it works dont touch it
        for (int i = 0; i < testBalls.size(); i++) {
            Circle ballA = testBalls.get(i);

            ballA.update(deltaTime);

            handleCollisionBalls(playerBall, ballA);

            collisionFromWalls(ballA);

            for (int j = i + 1; j < testBalls.size(); j++) {
                Circle ballB = testBalls.get(j);
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

    public void collisionFromWalls(Circle Ball) {
        if (Ball.x <= Ball.size || Ball.x >= width - Ball.size) {
            Ball.dx = (float) Ball.dx * -1f;
        }
        if (Ball.y <= Ball.size ||Ball.y >= height - Ball.size) {
            Ball.dy = (float) Ball.dy * -1f;
        }
    }

    public void handleInput(Circle playerBall) {

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

        // 1. Only run the physics if they are actually touching!
        if (distance <= playerBall.size + testBall.size) {

            float dirX = diffX / distance;
            float dirY = diffY / distance;

            // 2. The overlapping bs (mandatory to stop them from gluing together)
            float overlap = (playerBall.size + testBall.size) - distance;
            float pushAmount = (overlap / 2f) * 1.05f;

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

                // 3. Fixed parentheses so friction scales the entire sliding speed
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
