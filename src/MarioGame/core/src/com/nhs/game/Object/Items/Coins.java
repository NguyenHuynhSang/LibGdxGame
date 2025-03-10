package com.nhs.game.Object.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Object.Player.Mario;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.Screens.ScreenManagement;
import com.nhs.game.UiManager.Hud;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global.GROUND_BIT;
import static com.nhs.game.Global.global.ITEM_BIT;
import static com.nhs.game.Global.global.MARIO_BIT;
import static com.nhs.game.Global.global.NONCOLLISION_BIT;
import static com.nhs.game.Global.global.PPM;

public class Coins extends  Item {

    private Animation coinAni;
    private com.badlogic.gdx.utils.Array<TextureRegion> frames;
    public Coins(ScreenManagement screen, float x, float y) {
        super(screen, x, y);

        frames=new Array<TextureRegion>();
        for (int i=0;i<3;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("coin"),i*16,0,16,16));
        }
        coinAni=new Animation(0.1f,frames);
        setRegion( (TextureRegion)coinAni.getKeyFrame(0));
    }

    @Override
    public void defineItem() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type=BodyDef.BodyType.DynamicBody;
        body=world.createBody(bdef);
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        //PolygonShape shape=new PolygonShape();
        //shape.setAsBox(16/2/PPM,16/2/PPM);
        shape.setRadius(6/PPM);
        // mỗi fixture có category và mask riêng
        // category để nhận biết đó là object nào
        // mask là các object và object đang xét có thể va chạm
        fdef.filter.categoryBits=ITEM_BIT;
        fdef.filter.maskBits= MARIO_BIT|GROUND_BIT;
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);
        shape.dispose();
      //  body.setLinearVelocity(0,0f);
        body.setGravityScale(0);

//        velocity=new Vector2(0,3.0f);
//        body.setLinearVelocity(velocity);
       // body.applyLinearImpulse(new Vector2(0,3.8f),body.getWorldCenter(),true);
    }


    @Override
    public void useItem(Mario mario) {
        destroy();
        mariobros.manager.get("audio/sounds/coin.wav", Sound.class).play();
        Hud.UpdateScore(500);
    }

    @Override
    public void update(float dt) {


        super.update(dt);
        if (Destroyed) return;
        stateTimer+=dt;
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        setRegion( (TextureRegion)coinAni.getKeyFrame(stateTimer,true));


    }

    @Override
    public void draw(Batch batch) {
        if(!Destroyed)
        super.draw(batch);
    }
}
