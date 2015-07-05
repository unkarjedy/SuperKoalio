package com.unkarjedy.platformer.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.unkarjedy.platformer.PlatformerGame;

import static com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;


/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public class MainMenuScreen implements Screen {

    private static final float BUTTON_GAP_SIZE = 20;
    private PlatformerGame game;

    private Stage stage;
    private SpriteBatch batch;

    private GlyphLayout glyphLayout;
    private BitmapFont font;private Skin skin;
    private TextButtonStyle textButtonStyle;
    private Image backgroundImage;

    public MainMenuScreen(PlatformerGame game) {
        this.game = game;
//        create();
    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void update(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.setScreen(new LoadLevelScreen(game));
        }
    }

    private void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createBackgroundImage();

        createFontStyle();

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton playButton = new TextButton("Play", textButtonStyle);
        playButton.setPosition(centralizeX(playButton), Gdx.graphics.getHeight() / 2);
        playButton.setSize(150, 70);
        stage.addActor(playButton);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoadLevelScreen(game));
            }
        });

        final TextButton exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.setPosition(centralizeX(exitButton), playButton.getY() - playButton.getHeight() - BUTTON_GAP_SIZE);
        exitButton.setSize(150, 70);
        stage.addActor(exitButton);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.exit();
            }
        });

    }

    private void createBackgroundImage() {
        backgroundImage = new Image(new Texture("textures/jungle.jpg"));
        backgroundImage.setZIndex(0);
        backgroundImage.setWidth(stage.getHeight() * backgroundImage.getWidth() / backgroundImage.getHeight());
        backgroundImage.setHeight(stage.getHeight());
        backgroundImage.setX((stage.getWidth() - backgroundImage.getWidth()) / 2);
        stage.addActor(backgroundImage);
    }

    private float centralizeX(TextButton textButton) {
        return Gdx.graphics.getWidth() / 2 - textButton.getWidth() / 2;
    }

    private void createFontStyle() {
        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();
        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        font =  new BitmapFont(Gdx.files.internal("fonts/JungleRoarRegular.fnt"),false);
        // font.scale(1);
        skin.add("default", font);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.WHITE);
        textButtonStyle.down = skin.newDrawable("white", Color.GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);
    }


    @Override
    public void show() {
        create();
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
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
