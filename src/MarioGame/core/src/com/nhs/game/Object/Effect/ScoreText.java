package com.nhs.game.Object.Effect;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.nhs.game.Screens.ScreenManagement;

import static com.nhs.game.Global.global.NONCOLLISION_BIT;
import static com.nhs.game.Global.global.PPM;

public class ScoreText extends Effects   {

    private static Animation scoreAni;
    private static Array<TextureRegion> frames;
    private  static  boolean InitScore=false;
    public ScoreText(ScreenManagement screen, float x, float y) {
        super(screen, x, y);
        if (!InitScore){
            frames=new Array<TextureRegion>();
            for (int i=1;i<4;i++)
            {
                frames.add(new TextureRegion(screen.getAtlas().findRegion("score"),i*16,0,16,8));
            }
            scoreAni=new Animation(0.1f,frames);
            InitScore=true;
        }
        setRegion( (TextureRegion)scoreAni.getKeyFrame(0));
    }

    @Override
    protected void defineEffect() {
        setBounds(getX(),getY(),16/PPM,16/PPM);
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
        fdef.filter.categoryBits=NONCOLLISION_BIT;
        fdef.filter.maskBits= NONCOLLISION_BIT;
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);
        shape.dispose();
        velocity=new Vector2(0,3.0f);
        body.setLinearVelocity(velocity);
        // body.applyLinearImpulse(new Vector2(0,3.8f),body.getWorldCenter(),true);
    }
    @Override
    public void update(float dt) {
        super.update(dt);
        if (isDestroyed) {
            setRegion( (TextureRegion)scoreAni.getKeyFrame(stateTimer,true));
            return;
        }
        if (stateTimer>0.3f) {
            setDestroy=true;
        }
        stateTimer+=dt;
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        setRegion( (TextureRegion)scoreAni.getKeyFrame(stateTimer,true));
    }
}