package com.sweatshop.chessters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.stream.IntStream;

public class ChessBoardScreen implements Screen
{
    final ChesstersGame game;
    public ChessPiece[/*column*/][/*row*/] board;

    private final Texture hlghtTile;
    private final Texture slectTile;
    private final Texture whiteTile;
    private final Texture blackTile;

    private final Texture whiteRook;
    private final Texture whiteKing;
    private final Texture whiteQuen;
    private final Texture whiteBish;
    private final Texture whiteKngt;
    private final Texture whitePawn;

    private final Texture blackRook;
    private final Texture blackKing;
    private final Texture blackQuen;
    private final Texture blackBish;
    private final Texture blackKngt;
    private final Texture blackPawn;

    private ChessPiece.Team currentTurn;

    private int SelectedTileX;
    private int SelectedTileY;

    OrthographicCamera camera;
    FitViewport fitViewport;

    public ChessBoardScreen(final ChesstersGame game)
    {
        this.game = game;

        currentTurn = ChessPiece.Team.WHITE;
        SelectedTileX = SelectedTileY = -1;

        camera = new OrthographicCamera();
        fitViewport = new FitViewport(600, 600);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        board = new ChessPiece[8][8];

        hlghtTile = new Texture(Gdx.files.internal("htile.png"));
        slectTile = new Texture(Gdx.files.internal("stile.png"));
        whiteTile = new Texture(Gdx.files.internal("wtile.png"));
        blackTile = new Texture(Gdx.files.internal("btile.png"));

        whiteRook = new Texture(Gdx.files.internal("wrook.png"));
        whiteKing = new Texture(Gdx.files.internal("wking.png"));
        whiteQuen = new Texture(Gdx.files.internal("wquen.png"));
        whiteBish = new Texture(Gdx.files.internal("wbish.png"));
        whiteKngt = new Texture(Gdx.files.internal("wkngt.png"));
        whitePawn = new Texture(Gdx.files.internal("wpawn.png"));

        whiteRook.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        whiteKing.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        whiteQuen.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        whiteBish.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        whiteKngt.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        whitePawn.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        blackRook = new Texture(Gdx.files.internal("brook.png"));
        blackKing = new Texture(Gdx.files.internal("bking.png"));
        blackQuen = new Texture(Gdx.files.internal("bquen.png"));
        blackBish = new Texture(Gdx.files.internal("bbish.png"));
        blackKngt = new Texture(Gdx.files.internal("bkngt.png"));
        blackPawn = new Texture(Gdx.files.internal("bpawn.png"));

        blackRook.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        blackKing.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        blackQuen.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        blackBish.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        blackKngt.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        blackPawn.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    public boolean CheckBounds(float pointX, float pointY, float xMinBound, float xMaxBound, float yMinBound, float yMaxBound)
    {
        return (pointX >= xMinBound && pointX < xMaxBound &&
                pointY >= yMinBound && pointY < yMaxBound);
    }

    /*turn A1 -> 0*/
    public void GetIndex(String gridCode, int[] xy)
    {
        int xComponent = gridCode.charAt(0) - 'A';
        int yComponent = gridCode.charAt(1) - 1;
        xy[0] = xComponent;
        xy[1] = yComponent;
    }


    /*TO WELLSON: USE THIS!!!*/
    public void Move(int fromX, int fromY, int toX, int toY)
    {

    }

    /*TO WELLSON: USE THIS!!!*/
    public boolean ValidMove(int fromX, int fromY, int toX, int toY)
    {
        return false;
    }

    /*Move from A1 to B4*/
    public void Move(String from, String to)
    {
        int[] idxFromXY = new int[2], idxToXY = new int[2];
        GetIndex(from, idxFromXY);
        GetIndex(to, idxToXY);
        int idxFromX, idxFromY, idxToX, idxToY;
        idxFromX = idxFromXY[0];
        idxFromY = idxFromXY[1];
        idxToX = idxToXY[0];
        idxToY = idxToXY[1];
        Move(idxFromX, idxFromY, idxToX, idxToY);
    }

    public boolean ValidMove(String from, String to)
    {
        int[] idxFromXY = new int[2], idxToXY = new int[2];
        GetIndex(from, idxFromXY);
        GetIndex(to, idxToXY);
        int idxFromX, idxFromY, idxToX, idxToY;
        idxFromX = idxFromXY[0];
        idxFromY = idxFromXY[1];
        idxToX = idxToXY[0];
        idxToY = idxToXY[1];
        return ValidMove(idxFromX, idxFromY, idxToX, idxToY);
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

    public void HighlightTile(int x, int y)
    {
        game.batch.draw(hlghtTile, x * 75, y * 75, 75, 75);
    }

    public void HighlightBySelectedTileOffset(int xOffset, int yOffset)
    {
        if ((SelectedTileX + xOffset) < 8 && (SelectedTileY + yOffset) < 8 ) {
        HighlightTile(SelectedTileX + xOffset, SelectedTileY + yOffset);
        }
    }

    // to change from index to string, use to print
    public String ConvertIndexToString(int x, int y)
    {
        char xComponent = 'A';
        xComponent += (char)x;
        y += 1;
        String x1 = String.valueOf(xComponent);
        String y1 = "" + y;
        return x1 + y1;
    }

    @Override
    public void show()
    {
        Setup();
    }

    public void DrawPiece(Texture t, float x, float y)
    {
        game.pieceBatch.draw(t, x + 7.5f, y + 7.5f, 60, 60);
    }

    public void DrawChessPieces()
    {
        for (int x = 0; x < 8; ++x )
        {
            for (int y = 0; y < 8; ++y)
            {
                if (board[x][y] != null)
                {
                    if (board[x][y].team == ChessPiece.Team.WHITE)
                    {
                        switch (board[x][y].type)
                        {
                            case BISHOP:
                                DrawPiece(whiteBish, x * 75, y * 75);
                                break;
                            case KNIGHT:
                                DrawPiece(whiteKngt, x * 75, y * 75);
                                break;
                            case KING:
                                DrawPiece(whiteKing, x * 75, y * 75);
                                break;
                            case QUEEN:
                                DrawPiece(whiteQuen, x * 75, y * 75);
                                break;
                            case ROOK:
                                DrawPiece(whiteRook, x * 75, y * 75);
                                break;
                            case PAWN:
                                DrawPiece(whitePawn, x * 75, y * 75);
                                break;
                        }
                    }
                    else
                    {
                        switch (board[x][y].type) {
                            case BISHOP:
                                DrawPiece(blackBish, x * 75, y * 75);
                                break;
                            case KNIGHT:
                                DrawPiece(blackKngt, x * 75, y * 75);
                                break;
                            case KING:
                                DrawPiece(blackKing, x * 75, y * 75);
                                break;
                            case QUEEN:
                                DrawPiece(blackQuen, x * 75, y * 75);
                                break;
                            case ROOK:
                                DrawPiece(blackRook, x * 75, y * 75);
                                break;
                            case PAWN:
                                DrawPiece(blackPawn, x * 75, y * 75);
                                break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(float dt)
    {
        Vector3 mousePos = new Vector3();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            //bucket.x -= 200 * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            //bucket.x += 200 * dt;
        }


        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.setColor(Color.BLACK);
        game.font.draw(game.batch, "Current Selected", 600, 400);
        //Gdx.app.log("Chessters", String.format("SelectedTile(X, Y): (%d, %d)", SelectedTileX, SelectedTileY));
        for (int y = 0; y < 8; ++y)
        {
            for (int x = 0; x < 8; ++x)
            {
                if (SelectedTileX == x && SelectedTileY == y)
                {
                    game.batch.draw(slectTile, SelectedTileX * 75, SelectedTileY * 75, 75, 75);
                    //move logic
                }
                else
                {
                    if ((x + y) % 2 == 0)
                        game.batch.draw(whiteTile, x * 75, y * 75, 75, 75);
                    else
                        game.batch.draw(blackTile, x * 75, y * 75, 75, 75);
                }
                // if the click pos is within this tile's boundary
                // boundary is current tile's x to next tile's x
                // and current tile's y to next tile's y
                if (CheckBounds(mousePos.x, mousePos.y, x * 75, (x + 1) * 75, y * 75, (y + 1) * 75))
                {
                    if (SelectedTileX != x || SelectedTileY != y)
                    {
                        //check if tile has a piece
                        if (board[x][y] != null) {
                            game.batch.draw(hlghtTile, x * 75, y * 75, 75, 75);
                            if (Gdx.input.isTouched())
                            {
                                SelectedTileX = x;
                                SelectedTileY = y;
                            }
                        }
                    }
                }
            }
        }
        game.batch.draw(slectTile, 75 * 8, 10, 200, 50);
        if (CheckBounds(mousePos.x, mousePos.y, 75 * 8, 75 * 8 + 200, 10, 10 + 50))
        {
            if (Gdx.input.isTouched())
            {
                Gdx.app.log("Chessters", "Resetting!");
                Setup();
            }
        }
        if (SelectedTileX >= 0 && SelectedTileY >= 0)
        {
            if (board[SelectedTileX][SelectedTileY] != null) {
                if(currentTurn == ChessPiece.Team.WHITE) {
                    if (board[SelectedTileX][SelectedTileY].team == ChessPiece.Team.WHITE) {
                        switch (board[SelectedTileX][SelectedTileY].type) {
                            case BISHOP:
                                game.font.draw(game.batch, "W BISHOP", 600, 380);
                                break;
                            case KNIGHT:
                                game.font.draw(game.batch, "W KNIGHT", 600, 380);

                                    HighlightBySelectedTileOffset(1, 2);
                                    HighlightBySelectedTileOffset(-1, 2);
                                    HighlightBySelectedTileOffset(1, -2);
                                    HighlightBySelectedTileOffset(-1, -2);
                                    HighlightBySelectedTileOffset(2, 1);
                                    HighlightBySelectedTileOffset(2, -1);
                                    HighlightBySelectedTileOffset(-2, 1);
                                    HighlightBySelectedTileOffset(-2, -1);

                                break;
                            case KING:
                                game.font.draw(game.batch, "W KING", 600, 380);
                                break;
                            case QUEEN:
                                game.font.draw(game.batch, "W QUEEN", 600, 380);
                                break;
                            case ROOK:
                                game.font.draw(game.batch, "W ROOK", 600, 380);
                                for (int i = 0; i < 8; i++ ) {
                                        if (board[SelectedTileX][i] == null) {
                                            HighlightBySelectedTileOffset(0, i);
                                        }
                                        if (board[i][SelectedTileY] == null) {
                                            HighlightBySelectedTileOffset(i, 0);
                                        }
                                }
                                break;
                            case PAWN:
                                HighlightBySelectedTileOffset(0, 1);
                                game.font.draw(game.batch, "W PAWN", 600, 380);
                                if (SelectedTileY == 1) {
                                        HighlightBySelectedTileOffset(0, 2);
                                }
                                break;
                            default:
                                break;
                        }

                    }
                    else if(board[SelectedTileX][SelectedTileY].team == ChessPiece.Team.BLACK) {
                        switch (board[SelectedTileX][SelectedTileY].type) {
                            case BISHOP:
                                game.font.draw(game.batch, "B BISHOP", 600, 380);
                                break;
                            case KNIGHT:
                                game.font.draw(game.batch, "B KNIGHT", 600, 380);
                                break;
                            case KING:
                                game.font.draw(game.batch, "B KING", 600, 380);
                                break;
                            case QUEEN:
                                game.font.draw(game.batch, "B QUEEN", 600, 380);
                                break;
                            case ROOK:
                                game.font.draw(game.batch, "B ROOK", 600, 380);
                                break;
                            case PAWN:
                                game.font.draw(game.batch, "B PAWN", 600, 380);
                                break;
                            default:
                                break;
                            }
                    }
                }
                else {
                    if (board[SelectedTileX][SelectedTileY].team == ChessPiece.Team.WHITE) {
                        switch (board[SelectedTileX][SelectedTileY].type) {
                            case BISHOP:
                                game.font.draw(game.batch, "W BISHOP", 600, 380);
                                break;
                            case KNIGHT:
                                game.font.draw(game.batch, "W KNIGHT", 600, 380);
                                break;
                            case KING:
                                game.font.draw(game.batch, "W KING", 600, 380);
                                break;
                            case QUEEN:
                                game.font.draw(game.batch, "W QUEEN", 600, 380);
                                break;
                            case ROOK:
                                game.font.draw(game.batch, "W ROOK", 600, 380);
                                break;
                            case PAWN:
                                game.font.draw(game.batch, "W PAWN", 600, 380);
                                break;
                            default:
                                break;
                        }

                    }
                    else if(board[SelectedTileX][SelectedTileY].team == ChessPiece.Team.BLACK) {
                        switch (board[SelectedTileX][SelectedTileY].type) {
                            case BISHOP:
                                game.font.draw(game.batch, "B BISHOP", 600, 380);
                                break;
                            case KNIGHT:
                                game.font.draw(game.batch, "B KNIGHT", 600, 380);
                                break;
                            case KING:
                                game.font.draw(game.batch, "B KING", 600, 380);
                                break;
                            case QUEEN:
                                game.font.draw(game.batch, "B QUEEN", 600, 380);
                                break;
                            case ROOK:
                                game.font.draw(game.batch, "B ROOK", 600, 380);
                                break;
                            case PAWN:
                                HighlightBySelectedTileOffset(0, -1);
                                game.font.draw(game.batch, "B PAWN", 600, 380);
                                if (SelectedTileY == 6) {
                                    HighlightBySelectedTileOffset(0, -2);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }


            }
            String str = ConvertIndexToString(SelectedTileX, SelectedTileY);
            game.font.draw(game.batch,"Selected: " + str, 600, 350);
        }
        game.batch.end();

        game.pieceBatch.begin();
        DrawChessPieces();
        game.pieceBatch.end();
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
        hlghtTile.dispose();
        slectTile.dispose();
        whiteTile.dispose();
        blackTile.dispose();

        whiteRook.dispose();
        whiteKing.dispose();
        whiteQuen.dispose();
        whiteBish.dispose();
        whiteKngt.dispose();
        whitePawn.dispose();

        blackRook.dispose();
        blackKing.dispose();
        blackQuen.dispose();
        blackBish.dispose();
        blackKngt.dispose();
        blackPawn.dispose();
    }
}
