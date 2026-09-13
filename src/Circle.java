import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;

public class Circle {
    public float x;
    public float y;
    public float dx;
    public float dy;
    public float size;
    public Color color;

    public Circle(float startx, float starty, float startDx, float startDy, float startSize, Color startColor) {
        this.x = startx;
        this.y = starty;
        this.dx = startDx;
        this.dy = startDy;
        this.size = startSize;
        this.color = startColor;
    }

    public void update(float deltaTime) {
        this.x += this.dx * deltaTime;
        this.y += this.dy * deltaTime;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(this.color);
        shapeRenderer.circle(this.x, this.y, this.size);
    }
}
