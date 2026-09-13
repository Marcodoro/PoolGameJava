import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;

public class Square {
    public float x;
    public float y;
    public float size;
    public float speed;
    public float dx;
    public float dy;

    public Square(float startX, float startY, float startSize, float startspeed, float startdy, float startdx) {
        this.x = startX;
        this.y = startY;
        this.size = startSize;
        this.speed = startspeed;
        this.dx = startdx;
        this.dy = startdy;
    }
    public void update(float deltaTime) {
        this.x += this.dx * deltaTime;
        this.y += this.dy * deltaTime;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(this.x, this.y, this.size, this.size);
    }


}
