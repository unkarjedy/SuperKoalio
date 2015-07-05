package com.unkarjedy.platformer.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.unkarjedy.platformer.controller.PlayerStateListner;
import com.unkarjedy.platformer.model.LevelScore;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.utils.ResourceManager;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public class HUDRenderer implements PlayerStateListner {

    private Player player;
    private LevelScore levelScore;

    private Stage stage;
    private Batch batch;
    private Label label;
    private Texture heart;
    private Image[] hearts;

    private BitmapFont font;

    private static final int GAP_SIZE = 5;

    private Color defalutBatchColor;

    public HUDRenderer(Player player, LevelScore levelScore) {
        this.player = player;
        this.levelScore = levelScore;

        create();
    }


    private void create() {
        stage = new Stage();

        heart = ResourceManager.get("heart.png", Texture.class);

        hearts = new Image[player.DEFAULT_LIVES];
        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new Image(heart);
            hearts[i].setPosition(i * heart.getWidth() + GAP_SIZE, stage.getHeight() - heart.getHeight() - GAP_SIZE);
            stage.addActor(hearts[i]);
        }

        font = ResourceManager.get("JungleRoarRegular.fnt", BitmapFont.class);


        label = new Label(levelScore.buildScoreString(), new Label.LabelStyle(font, Color.BLUE));
        label.setPosition(hearts[0].getX(), hearts[0].getY() - hearts[0].getHeight() - GAP_SIZE);

        stage.addActor(label);

        batch = stage.getBatch();
        defalutBatchColor = stage.getBatch().getColor();
    }


    public void render() {
        stage.draw();
    }


    public void resize(int width, int height) {
    }

    @Override
    public void playerDead() {

    }

    @Override
    public void playerIsHit(boolean respawn) {
        for (int life = player.getLives(); life < hearts.length; life++) {
            hearts[life].setColor(Color.BLACK);
        }
    }

    @Override
    public void playerGetsCoin() {
        label.setText(levelScore.buildScoreString());
    }

    @Override
    public void playerReachedLevelFinish() {

    }
}
