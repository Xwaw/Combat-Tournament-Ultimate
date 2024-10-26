package MapGame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static ct.game.main.GameMain.PPM;

public class PhysicsManager {
    private World world;
    private Box2DDebugRenderer debugRenderer;

    public World getWorld() {
        return world;
    }

    public PhysicsManager(Vector2 gravity){
        world = new World(gravity, true);
        debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(new CollisionsManager());
    }

    private Body createSimpleBody(Vector2 position, boolean isStatic){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        return world.createBody(bodyDef);
    }

    public Body createBoxBodyForObject(Vector2 position, float[] hitBoxDimensions, float friction, String nameOfObject){
        Body body = createSimpleBody(position.scl(1 / PPM), true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((hitBoxDimensions[0] / 2) / PPM, (hitBoxDimensions[1] / 2) / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 0f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(nameOfObject);

        shape.dispose();

        return body;
    }

    public Body createBoxBodyForEntity(Vector2 position, float[] hitBoxSize){
        Body body = createSimpleBody(position.scl(1 / PPM), false);

        //Creating shape of collision for body
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((hitBoxSize[0] / 2) / PPM, (hitBoxSize[1] / 2) / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.1f;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");

        shape.dispose();

        return body;
    }

    public void updateWorldComponents(float deltaTime){
        world.step(deltaTime, 6, 2);
    }

    public void renderHitboxObjects(Camera camera){
        debugRenderer.render(world, camera.combined);
    }

    public void dispose(){
        world.dispose();
        debugRenderer.dispose();
    }
}
