package com.sweatshop.chessters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ChesstersGame extends Game
{
	public SpriteBatch batch;
	public SpriteBatch pieceBatch;
	public BitmapFont font;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		pieceBatch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new ChessBoardScreen(this));
	}

	@Override
	public void render()
	{
		super.render();
	}
	
	@Override
	public void dispose()
	{
		pieceBatch.dispose();
		batch.dispose();
		font.dispose();
	}
}
