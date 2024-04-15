package com.sweatshop.chessters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ChessBoardScreen implements Screen
{
    final ChesstersGame game;
    public ChessPiece[][] board;
    private ShapeRenderer shape;
    private Rectangle bucket;
    OrthographicCamera camera;
    FitViewport fitViewport;

    public ChessBoardScreen(final ChesstersGame game)
    {
        this.game = game;

        camera = new OrthographicCamera();
        fitViewport = new FitViewport(600, 600);
        camera.setToOrtho(false, 600, 600);

        shape = new ShapeRenderer();
        bucket = new Rectangle();

        board = new ChessPiece[8][8];

    }

    /*turn A1 -> 0*/
    public int GetIndex(String gridCode)
    {
        return 0;
    }

    /*Move from A1 to B4*/
    public void Move(String from, String to)
    {
        int idx_from, idx_to;
        idx_from = GetIndex(from);
        idx_to = GetIndex(to);
    }

    public boolean ValidMove()
    {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt)
    {
        if (Gdx.input.isTouched())
        {
            Vector3 clickPos = new Vector3();
            clickPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(clickPos);
            bucket.x = clickPos.x - 32;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= 200 * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += 200 * dt;

        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;


        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (int x = 0; x < 8; ++x)
        {
            for (int y = 0; y < 8; ++y)
            {
                shape.begin(ShapeRenderer.ShapeType.Filled);
                if ((x + y) % 2 == 0)
                    shape.setColor(new Color(0.23f, 0.15f, 0.1f, 1));
                else
                    shape.setColor(new Color(0.75f, 0.55f, 0.3f, 1));
                shape.rect(x * 75, y * 75, 75, 75);
                shape.end();
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

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
}
