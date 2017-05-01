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
        bdef.position.set(32 / Freaks.PPM, 32 / Freaks.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Freaks.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}

