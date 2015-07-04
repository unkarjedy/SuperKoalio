package com.unkarjedy.platformer.view;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.unkarjedy.platformer.PlatformerGame;
import com.unkarjedy.platformer.utils.ResourceManager;

import static com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;


/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public class MainMenu implements Screen {

    private PlatformerGame game;

    private Stage stage;
    private SpriteBatch batch;

    private GlyphLayout glyphLayout;
    private BitmapFont font;private Skin skin;
    private TextButtonStyle textButtonStyle;
    private Image backgroundImage;

    public MainMenu(PlatformerGame game) {
        this.game = game;
//        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createBackgroundImage();

        createFontStyle();

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton textButton=new TextButton("Play", textButtonStyle);
        textButton.setPosition(centralizeX(textButton), Gdx.graphics.getHeight() / 2);
        textButton.setSize(150, 70);
        stage.addActor(textButton);

        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SplashScreen(game));
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
