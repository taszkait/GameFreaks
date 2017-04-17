package it.taszka.freaks.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import it.taszka.freaks.Freaks;
import it.taszka.freaks.Scenes.Hud;

public class PlayScreen implements Screen {

    private Freaks game;
    private OrthographicCamera gameCame;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    public PlayScreen(Freaks game){
        this.game = game;
        //create camera used to follow freak through camera world
        gameCame = new OrthographicCamera();
        //create a FitViewport to maintain virtual aspect despite screen size

        gamePort = new FitViewport(Freaks.v_width,Freaks.v_height,gameCame);
        //create HUD for scores/timers/level info
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCame.position.set(gamePort.getScreenWidth()/2, gamePort.getScreenHeight()/2, 0);
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isTouched())
            gameCame.position.x += 100*dt;
    }

    public void update (float dt){
        handleInput(dt);
        gameCame.update();
        renderer.setView(gameCame);
    }

    @Override
    public void render(float delta) {
        update(delta);
        //color background
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private class load extends TiledMap {
        public load(String s) {
        }
    }
}
