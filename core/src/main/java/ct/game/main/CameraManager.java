package ct.game.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static ct.game.main.GameMain.PPM;

public class CameraManager {
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private float offSetCameraX = 0, offSetCameraY = 0;

    private final float minZoom = 0.1f;
    private final float maxZoom = 2.0f;

    private float cameraDamping = 0.1f;

    private final Vector2 targetPosition;

    public CameraManager(Viewport viewport) {
        this.camera = (OrthographicCamera) viewport.getCamera();
        this.viewport = viewport;
        this.targetPosition = new Vector2();
    }

    public void update() {
        camera.position.x += (((targetPosition.x / PPM) - camera.position.x) * cameraDamping) + (offSetCameraX / PPM);
        camera.position.y += (((targetPosition.y / PPM) - camera.position.y) * cameraDamping) + (offSetCameraY / PPM);
        camera.update();
    }

    public void setCameraDamping(float damping) {
        this.cameraDamping = damping;
    }

    public void setOffsetsOfCamera(float x, float y) {
        this.offSetCameraX = x;
        this.offSetCameraY = y;
    }

    public void follow(Vector2 target) {
        this.targetPosition.set(target);
    }

    public void setZoom(float zoom) {
        camera.zoom = Math.min(maxZoom, Math.max(minZoom, zoom));
    }

    public float getZoom() {
        return camera.zoom;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
