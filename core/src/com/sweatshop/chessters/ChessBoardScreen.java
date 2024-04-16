package com.sweatshop.chessters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ChessBoardScreen implements Screen
{
    final ChesstersGame game;
    public ChessPiece[/*column*/][/*row*/] board;
    private Rectangle bucket;

    private Texture HlghtTile;
    private Texture SlectTile;

    private Texture WhiteTile;
    private Texture BlackTile;

    private Texture WhiteRook;
    private Texture WhiteKing;
    private Texture WhiteQuen;
    private Texture WhiteBish;
    private Texture WhiteKngt;
    private Texture WhitePawn;

    private Texture BlackRook;
    private Texture BlackKing;
    private Texture BlackQuen;
    private Texture BlackBish;
    private Texture BlackKngt;
    private Texture BlackPawn;


    private int SelectedTileX;
    private int SelectedTileY;

    OrthographicCamera camera;
    FitViewport fitViewport;

    public ChessBoardScreen(final ChesstersGame game)
    {
        this.game = game;

        SelectedTileX = SelectedTileY = -1;

        camera = new OrthographicCamera();
        fitViewport = new FitViewport(600, 600);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bucket = new Rectangle();

        board = new ChessPiece[8][8];

        HlghtTile = new Texture(Gdx.files.internal("htile.png"));
        SlectTile = new Texture(Gdx.files.internal("stile.png"));
        WhiteTile = new Texture(Gdx.files.internal("wtile.png"));
        BlackTile = new Texture(Gdx.files.internal("btile.png"));

        WhiteRook = new Texture(Gdx.files.internal("wrook.png"));
        WhiteKing = new Texture(Gdx.files.internal("wking.png"));
        WhiteQuen = new Texture(Gdx.files.internal("wquen.png"));
        WhiteBish = new Texture(Gdx.files.internal("wbish.png"));
        WhiteKngt = new Texture(Gdx.files.internal("wkngt.png"));
        WhitePawn = new Texture(Gdx.files.internal("wpawn.png"));

        WhiteRook.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        WhiteKing.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        WhiteQuen.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        WhiteBish.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        WhiteKngt.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        WhitePawn.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        BlackRook = new Texture(Gdx.files.internal("brook.png"));
        BlackKing = new Texture(Gdx.files.internal("bking.png"));
        BlackQuen = new Texture(Gdx.files.internal("bquen.png"));
        BlackBish = new Texture(Gdx.files.internal("bbish.png"));
        BlackKngt = new Texture(Gdx.files.internal("bkngt.png"));
        BlackPawn = new Texture(Gdx.files.internal("bpawn.png"));

        BlackRook.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        BlackKing.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        BlackQuen.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        BlackBish.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        BlackKngt.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        BlackPawn.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


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
    
    public void Setup()
    {
        board[0][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.ROOK);
        board[1][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.KNIGHT);
        board[2][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.BISHOP);
        board[3][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.KING);
        board[4][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.QUEEN);
        board[5][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.BISHOP);
        board[6][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.KNIGHT);
        board[7][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.ROOK);
        board[0][1] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.PAWN);
        board[1][1] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.PAWN);
        board[2][1] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.PAWN);
        board[3][1] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.PAWN);
        board[4][1] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.PAWN);
        board[5][1] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.PAWN);
        board[6][1] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.PAWN);
        board[7][1] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.PAWN);

        board[0][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.ROOK);
        board[1][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.KNIGHT);
        board[2][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.BISHOP);
        board[3][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.KING);
        board[4][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.QUEEN);
        board[5][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.BISHOP);
        board[6][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.KNIGHT);
        board[7][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.ROOK);
        board[0][6] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.PAWN);
        board[1][6] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.PAWN);
        board[2][6] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.PAWN);
        board[3][6] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.PAWN);
        board[4][6] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.PAWN);
        board[5][6] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.PAWN);
        board[6][6] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.PAWN);
        board[7][6] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.PAWN);
    }

    public boolean ValidMove()
    {
        return false;
    }

    @Override
    public void show()
    {
        Setup();
    }

    public void DrawPiece(Texture t, float x, float y)
    {
        game.batch.draw(t, x + 7.5f, y + 7.5f, 60, 60);
    }

    @Override
    public void render(float dt)
    {
        Vector3 mousePos = new Vector3();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= 200 * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += 200 * dt;


        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        //Gdx.app.log("Chessters", String.format("SelectedTile(X, Y): (%d, %d)", SelectedTileX, SelectedTileY));
        for (int y = 0; y < 8; ++y)
        {
            for (int x = 0; x < 8; ++x)
            {
                if (SelectedTileX == x && SelectedTileY == y)
                {
                    game.batch.draw(SlectTile, SelectedTileX * 75, SelectedTileY * 75, 75, 75);
                }
                else
                {
                    if ((x + y) % 2 == 0)
                        game.batch.draw(WhiteTile, x * 75, y * 75, 75, 75);
                    else
                        game.batch.draw(BlackTile, x * 75, y * 75, 75, 75);
                }

                // if the click pos is within this tile's boundary
                // boundary is current tile's x to next tile's x
                // and current tile's y to next tile's y
                if (mousePos.x > x * 75 && mousePos.x <= (x + 1) * 75 &&
                    mousePos.y > y * 75 && mousePos.y <= (y + 1) * 75)
                {
                    if (SelectedTileX != x || SelectedTileY != y)
                    {
                        //check if tile has a piece
                        if (board[x][y] != null) {
                            game.batch.draw(HlghtTile, x * 75, y * 75, 75, 75);
                            if (Gdx.input.isTouched())
                            {
                                SelectedTileX = x;
                                SelectedTileY = y;
                            }
                        }
                    }
                }

                if (board[x][y] != null)
                {
                    if (board[x][y].team == ChessPiece.Team.WHITE)
                    {
                        switch (board[x][y].type)
                        {
                            case BISHOP:
                                DrawPiece(WhiteBish, x * 75, y * 75);
                                break;
                            case KNIGHT:
                                DrawPiece(WhiteKngt, x * 75, y * 75);
                                break;
                            case KING:
                                DrawPiece(WhiteKing, x * 75, y * 75);
                                break;
                            case QUEEN:
                                DrawPiece(WhiteQuen, x * 75, y * 75);
                                break;
                            case ROOK:
                                DrawPiece(WhiteRook, x * 75, y * 75);
                                break;
                            case PAWN:
                                DrawPiece(WhitePawn, x * 75, y * 75);
                                break;
                        }
                    }
                    else
                    {

                        switch (board[x][y].type)
                        {
                            case BISHOP:
                                DrawPiece(BlackBish, x * 75, y * 75);
                                break;
                            case KNIGHT:
                                DrawPiece(BlackKngt, x * 75, y * 75);
                                break;
                            case KING:
                                DrawPiece(BlackKing, x * 75, y * 75);
                                break;
                            case QUEEN:
                                DrawPiece(BlackQuen, x * 75, y * 75);
                                break;
                            case ROOK:
                                DrawPiece(BlackRook, x * 75, y * 75);
                                break;
                            case PAWN:
                                DrawPiece(BlackPawn, x * 75, y * 75);
                                break;
                        }
                    }
                }
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
