package com.nhs.game.Screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nhs.game.Engine.Controller;
import com.nhs.game.Screens.PlayScreen.FirstScreen;
import com.nhs.game.mariobros;

import static com.nhs.game.Global.global._height;
import static com.nhs.game.Global.global._width;

public class GameOver implements Screen {
    private Viewport viewport;
    private Stage   stage;
    private Controller gameController;
    private Game game;
    public  GameOver(Game game){
        this.game=game;
        viewport=new FitViewport(_width,_height,new OrthographicCamera());
        stage=new Stage(viewport,((mariobros)game).getBatch());
        Label.LabelStyle font=new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table=new Table();
        table.center();
        table.setFillParent(true);
        Label gameOverLable=new Label("GAME OVER ",font);
        Label replayLable=new Label("Click to replay",font);
        table.add(gameOverLable).expandX();
        table.row();
        table.add(replayLable).expandX().padTop(10);
        stage.addActor(table);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            game.setScreen(new FirstScreen((mariobros)game));
            dispose();
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public  void hide() {

    }

    @Override
    public void dispose() {


        stage.dispose();
        Gdx.app.log("Dispose","game over screen");
    }
}
