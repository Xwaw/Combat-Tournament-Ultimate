package Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionChecker{
    private RayCastCallback rayCastCallback;

    public CollisionChecker() {
        rayCastCallback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getUserData() != null && fixture.getUserData().equals("ground")) {
                    return 0;
                }
                return 1;
            }
        };
    }

    public boolean isGrounded(World world, Body playerBody) {
        Vector2 rayStart = playerBody.getPosition();
        PolygonShape shape = (PolygonShape) playerBody.getFixtureList().get(0).getShape();
        Vector2 size = new Vector2();
        shape.getVertex(0, size);

        float hitboxHeight = size.y;
        float yOffSet = 2.7f;

        Vector2 rayStartAdjusted = new Vector2(rayStart.x, rayStart.y - hitboxHeight);
        Vector2 rayEnd = new Vector2(rayStart.x, rayStartAdjusted.y - yOffSet);

        final boolean[] bodyOnFloor = {false};

        world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getUserData() != null && fixture.getUserData().equals("Floor")) {
                    bodyOnFloor[0] = true;
                    return 0;
                }
                return 1;
            }
        }, rayStartAdjusted, rayEnd);

        return bodyOnFloor[0];
    }
}
