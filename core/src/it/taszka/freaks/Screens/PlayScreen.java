package it.taszka.freaks.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.taszka.freaks.Freaks;
import it.taszka.freaks.Scenes.Hud;
import it.taszka.freaks.Sprites.Freak;
import it.taszka.freaks.Tools.B2WorldCreator;


public class PlayScreen implements Screen {

    private Freaks game;
    private Freak player;
    private OrthographicCamera gameCame;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;



    public PlayScreen(Freaks game) {
        this.game = game;
        //create camera used to follow freak through camera world
        gameCame = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect despite screen size
        gamePort = new FitViewport(Freaks.v_width / Freaks.PPM, Freaks.v_height / Freaks.PPM, gameCame);

        //create HUD for scores/timers/level info
        hud = new Hud(game.batch);

        //load our map and stepu our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1 / Freaks.PPM);

        //initially set our gameCame to be correctly at the start of map
        gameCame.position.set(gamePort.getScreenWidth() / 2, gamePort.getScreenHeight() / 2, 0);

        //Vector2 for gravity
        world = new World(new Vector2(0, -10), true);

        //allows for debug lines of our box2d world
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        //initialization of Freak class object
        player = new Freak(world);

    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        //if our user is holding down mose move our camera through the game world
        /*if(Gdx.input.isTouched())
            gameCame.position.x += 100*dt; */
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update (float dt){
        //handle user input first
        handleInput(dt);

        world.step(1/60f, 6, 2);

        gameCame.position.x = player.b2body.getPosition().x;

        //update our gameCame with correct coordinates after changes
        gameCame.update();
        //tell our renderer to draw only what our camera can see in our game world
        renderer.setView(gameCame);
    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);
        //color background
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renderour game map
        renderer.render();

        //render our Box2DDebugLines
        b2dr.render(world, gameCame.combined);

        //Set our batch to now draw what the Hud camera sees
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }

    private class load extends TiledMap {
        public load(String s) {
        }
    }
}
