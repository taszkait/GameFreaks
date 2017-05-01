package it.taszka.freaks.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import it.taszka.freaks.Freaks;

public class Freak extends SpriteBatch {
    public World world;
    public Body b2body;

    public Freak (World world){
        this.world = world;
        defineFreak();
    }

    public void defineFreak(){
        BodyDef bdef = new BodyDef();
        // set our body's starting position in the world
        bdef.position.set(32 / Freaks.PPM, 32 / Freaks.PPM);
        // we set our body to dynamic
        bdef.type = BodyDef.BodyType.DynamicBody;
        // create our body in the world using our body definition
        b2body = world.createBody(bdef);

        // Create our fixture and attach it to the body
        FixtureDef fdef = new FixtureDef();
        // Create a circle shape and set its radius to 5
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Freaks.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}

