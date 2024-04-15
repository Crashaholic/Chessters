package com.sweatshop.chessters;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class ChesstersGame extends ApplicationAdapter
{
	SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private Texture bucketTexture;
	private ShapeRenderer shape;
	
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600, 600);

		bucket = new Rectangle(368, 20, 64, 64);
		bucketTexture = new Texture("1007PART2TUT.png");
		shape = new ShapeRenderer();
	}

	@Override
	public void render()
	{
		if (Gdx.input.isTouched())
		{
			Vector3 clickPos = new Vector3();
			clickPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(clickPos);
			bucket.x = clickPos.x - 32;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();

		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 800 - 64)
			bucket.x = 800 - 64;

		ScreenUtils.clear(1, 1, 1, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int x = 0; x < 8; ++x)
		{
			for (int y = 0; y < 8; ++y)
			{
				shape.begin(ShapeRenderer.ShapeType.Filled);
				if ((x + y) % 2 == 0)
					shape.setColor(Color.BLACK);
				else
					shape.setColor(Color.WHITE);
				shape.rect(x * 75, y * 75, 75, 75);
				shape.end();
			}
		}
		batch.end();
	}
	
	@Override
	public void dispose()
	{
		bucketTexture.dispose();
		img.dispose();
		batch.dispose();
	}
}
