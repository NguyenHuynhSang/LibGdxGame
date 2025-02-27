package com.nhs.game.Object.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.nhs.game.Object.Effect.EffectDef;
import com.nhs.game.Object.Effect.ScoreText;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.ScreenManagement;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.BRICK_BIT;
import static com.nhs.game.Global.global.COINS_BIT;
import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.ITEM_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.PIPE_BIT;
import static com.nhs.game.Global.global.PPM;

public class Mushroom extends Item {
    public Mushroom(ScreenManagement screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("mushroom"),0,0,16,16);
        velocity=new Vector2(0.5f,0);

    }

    @Override
    public void defineItem() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type=BodyDef.BodyType.DynamicBody;
        body=world.createBody(bdef);
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6/PPM);
        // mỗi fixture có category và mask riêng
        // category để nhận biết đó là object nào
        // mask là các object và object đang xét có thể va chạm
        fdef.filter.categoryBits=ITEM_BIT;
        fdef.filter.maskBits= GROUND_BIT |MARIO_BIT| COINS_BIT |BRICK_BIT| PIPE_BIT;
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }

    @Override
    public void useItem(Mario mario) {
        destroy();
        if (!mario.isBig)
        {
            (screen).spawnEffect(new EffectDef(new Vector2(body.getPosition().x,body.getPosition().y+16/PPM),
                    ScoreText.class));
            mario.Grow();
        }
        else
        mariobros.manager.get("audio/sounds/lifeup.wav",Sound.class).play();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (Destroyed) return;
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        velocity.y=body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }

    @Override
    public void draw(Batch batch) {
        if (!Destroyed)
        super.draw(batch);
    }
}
