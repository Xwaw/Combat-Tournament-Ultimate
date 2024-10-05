package MapGame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsManager {
    private World world;
    private Box2DDebugRenderer debugRenderer;

    public PhysicsManager(Vector2 gravity){
        world = new World(gravity, false);

        debugRenderer = new Box2DDebugRenderer();
    }

    public Body createBody(Vector2 position, float[] hitBoxSize ,boolean staticObject){
        //Creating logic of Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = staticObject ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        //Creating shape of collision for body
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hitBoxSize[0], hitBoxSize[1]);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = staticObject ? 0 : 1;
        fixtureDef.friction = 0.5f;

        //Setting body
        body.createFixture(shape, 1.0f);

        shape.dispose();

        return body;
    }

    public World getWorld(){
        return world;
    }

    public void updateWorldComponents(){
        world.step(1/60f, 6, 2);
    }

    public void renderHitboxObjects(Camera camera){
        debugRenderer.render(world, camera.combined);
    }

    public void dispose(){
        world.dispose();
        debugRenderer.dispose();
    }
}
