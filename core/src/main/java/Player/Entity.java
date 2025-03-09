package Player;

import MapGame.PhysicsManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static ct.game.main.GameMain.PPM;

public class Entity {
    // Variables about stats of Entity/Player
    private int speed;
    private float health;
    private int maxHealth;
    private float special;
    private int maxSpecial;
    private float[] multiplierDamage;
    private int kills;
    private int comboCounter;
    // Variables for additional stats
    private int sizeOfCharacter;
    private int punchSpeed;
    private int damage;
    private int regenOfSpecial;
    private int regenOfHealth;
    private int jumpStrength;
    private int maxAirJumps;
    private int airJumps;

    // Variables about traits of Entity/Player (physics, hitBox etc...)
    private float[] physicsBoxSize;
    private Body body;

    private PlayerController playerController;
    private EntityGraphicsManager graphicsEntity;

    public Entity(SpriteBatch batch, int maxHealth, int special, , Vector2 position){
        this.speed = 10;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.special = special;
        this.maxSpecial = 100;
        this.multiplierDamage = new float[]{0.5f, 1.0f, 1.5f};
        this.kills = 0;
        this.comboCounter = 0;

        this.sizeOfCharacter = 1;
        this.punchSpeed = 1;
        this.damage = 1;
        this.regenOfSpecial = 1;
        this.regenOfHealth = 1;
        this.jumpStrength = 25;
        this.maxAirJumps = 2;
        this.airJumps = maxAirJumps;

        this.physicsBoxSize = new float[]{44f, 129f};
        this.playerController = new PlayerController();
        this.graphicsEntity = new EntityGraphicsManager();
        this.body = new PhysicsManager(new Vector2(0, -9.8f)).createBoxBodyForEntity(new Vector2(position), physicsBoxSize);
    }

    public void update(float deltaTime){
        graphicsEntity.updateIsPlayerOnAir();
        graphicsEntity.updatePlayerGraphicsComponents(deltaTime);
        graphicsEntity.renderPlayerHitBox();

        regenerateSpecialBar(regenOfSpecial);
    }

    public Body getBody() {
        return body;
    }

    public float getHealth() {
        return health;
    }
    public void setHealth(float healthPoints) {
        this.health = Math.min(healthPoints, 100);
    }
    public float getSpecial() {
        return special;
    }

    public void setSpecialPoints(float specialPoints){
        this.special = Math.min(specialPoints, 100);
    }
    private void regenerateSpecialBar(float progressRegen){
        if(special < 100.0f && !playerController.isSpecialReady){
            special += progressRegen;
        }else{
            playerController.isSpecialReady = true;
        }
    }

    public int getAirJumps(){
        return airJumps;
    }
    public float getJumpStrength() {
        return jumpStrength;
    }

    public void setAirJumps(int jumps){
        airJumps = jumps;
    }

    public float getBodyPositionX(){
        return body.getPosition().x * PPM;
    }
    public float getBodyPositionY(){
        return body.getPosition().y * PPM;
    }
    public Vector2 getBodyPositions(){
        return new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }

    public boolean isOnGround(){
        return collisionChecker.isGrounded(physicsManager.getWorld(), body);
    }
}
