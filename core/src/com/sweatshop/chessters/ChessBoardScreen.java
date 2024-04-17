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

    private final Texture buttonTexture;

    private ChessPiece.Team currentTurn;

    private int selectedTileX;
    private int selectedTileY;

    private ChessTiming globalTimer;
    private ChessTiming whiteTimer;
    private ChessTiming blackTimer;

    private boolean gameStarted;

    OrthographicCamera camera;
    FitViewport fitViewport;

    public ChessBoardScreen(final ChesstersGame game)
    {
        this.game = game;

        camera = new OrthographicCamera();
        fitViewport = new FitViewport(600, 600);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        board = new ChessPiece[8][8];

        hlghtTile = new Texture(Gdx.files.internal("htile.png"));
        slectTile = new Texture(Gdx.files.internal("stile.png"));
        whiteTile = new Texture(Gdx.files.internal("wtile.png"));
        blackTile = new Texture(Gdx.files.internal("btile.png"));

        hlghtTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        slectTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        whiteTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        blackTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

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

        buttonTexture = new Texture(Gdx.files.internal("button.png"));
        buttonTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    public void DrawText(String text, float x, float y)
    {
        game.font.draw(game.batch, text, x, y);
    }

    public boolean CheckBounds(float pointX, float pointY, float xMinBound, float xMaxBound, float yMinBound, float yMaxBound)
    {
        return (pointX >= xMinBound && pointX < xMaxBound &&
                pointY >= yMinBound && pointY < yMaxBound);
    }

    public boolean Button(Vector3 mousePos, String text, float x, float y, float width, float height)
    {
        game.batch.draw(buttonTexture, x, y, width, height);
        DrawText(text, x + 5, y + height / 1.5f);
        if (CheckBounds(mousePos.x, mousePos.y, x, x + width, y, y + height))
        {
            return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
        }
        return false;
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
        //TODO: THIS THANG
        if (!ValidMove(fromX, fromY, toX, toY))
        {
            if (currentTurn == ChessPiece.Team.WHITE)
            {
                if (!gameStarted)
                {
                    gameStarted = true;
                }
                currentTurn = ChessPiece.Team.BLACK;
            }
            else
            {
                currentTurn = ChessPiece.Team.WHITE;
            }
        }
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
        globalTimer = new ChessTiming();
        whiteTimer = new ChessTiming(300.f);
        blackTimer = new ChessTiming(300.f);
        gameStarted = false;

        currentTurn = ChessPiece.Team.WHITE;
        selectedTileX = selectedTileY = -1;

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
        if (CheckBounds(selectedTileX + xOffset, selectedTileY + yOffset,
                0, 8, 0, 8))
        {
            HighlightTile(selectedTileX + xOffset, selectedTileY + yOffset);
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

        if (gameStarted)
        {
            if (currentTurn == ChessPiece.Team.WHITE)
            {
                whiteTimer.Decrement(dt);
            }
            else
            {
                blackTimer.Decrement(dt);
            }
            globalTimer.Increment(dt);
        }

        //if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        //{
            //bucket.x -= 200 * dt;
        //}
        //if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        //{
            //bucket.x += 200 * dt;
        //}


        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.setColor(Color.BLACK);
        DrawText("Current Selected", 600, 400);
        DrawText("Game Timer: " + globalTimer.GetMinutesString() + ":" + globalTimer.GetSecondsString(), 600, 420);
        DrawText("White Timer: " + whiteTimer.GetMinutesString() + ":" + whiteTimer.GetSecondsString(), 600, 320);
        DrawText("Black Timer: " + blackTimer.GetMinutesString() + ":" + blackTimer.GetSecondsString(), 600, 520);
        for (int y = 0; y < 8; ++y)
        {
            for (int x = 0; x < 8; ++x)
            {
                if (selectedTileX == x && selectedTileY == y)
                {
                    game.batch.draw(slectTile, selectedTileX * 75, selectedTileY * 75, 75, 75);
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
                    if (selectedTileX != x || selectedTileY != y)
                    {
                        //check if tile has a piece
                        if (board[x][y] != null) {
                            game.batch.draw(hlghtTile, x * 75, y * 75, 75, 75);
                            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
                            {
                                selectedTileX = x;
                                selectedTileY = y;
                            }
                        }
                    }
                    else
                    {
                        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
                        {
                            selectedTileX = -1;
                            selectedTileY = -1;
                        }
                    }
                }
            }
        }

        if (Button(mousePos, "Reset Game", 75 * 8, 10, 200, 50))
        {
            Gdx.app.log("Chessters", "Resetting!");
            Setup();
        }

        if (Button(mousePos, "Next turn", 75 * 8, 70, 200, 50))
        {
            Move("A1", "A2");
        }

        if (selectedTileX >= 0 && selectedTileY >= 0) {
            if (board[selectedTileX][selectedTileY] != null) {
                if(currentTurn == ChessPiece.Team.WHITE) {
                    if (board[selectedTileX][selectedTileY].team == ChessPiece.Team.WHITE) {
                        switch (board[selectedTileX][selectedTileY].type) {
                            case BISHOP:
                                DrawText("W BISHOP", 600, 380);
                                for (int i = 1; i < 8; i++ ) {
                                    HighlightBySelectedTileOffset(i, i);
                                    HighlightBySelectedTileOffset(-i, -i);
                                    HighlightBySelectedTileOffset(i, -i);
                                    HighlightBySelectedTileOffset(-i, i);
                                }

                                break;
                            case KNIGHT:
                                DrawText("W KNIGHT", 600, 380);
                                game.font.draw(game.batch, "W KNIGHT", 600, 380);
                                if (CheckBounds(selectedTileX + 1, selectedTileY + 2,
                                        0, 8, 0, 8)) {
                                    if (board[selectedTileX + 1][selectedTileY + 2] == null)
                                        HighlightBySelectedTileOffset(1, 2);
                                }

                                if (CheckBounds(selectedTileX - 1, selectedTileY + 2,
                                        0, 8, 0, 8)) {
                                    if (board[selectedTileX - 1][selectedTileY + 2] == null)
                                        HighlightBySelectedTileOffset(-1, 2);
                                }

                                if (CheckBounds(selectedTileX + 1, selectedTileY - 2,
                                        0, 8, 0, 8)) {
                                    if (board[selectedTileX+1][selectedTileY-2] == null)
                                        HighlightBySelectedTileOffset(1, -2);
                                }

                                if (CheckBounds(selectedTileX - 1, selectedTileY - 2,
                                        0, 8, 0, 8)) {
                                    if (board[selectedTileX-1][selectedTileY-2] == null)
                                        HighlightBySelectedTileOffset(-1, -2);
                                }

                                if (CheckBounds(selectedTileX + 2, selectedTileY + 1,
                                        0, 8, 0, 8)) {
                                    if (board[selectedTileX+2][selectedTileY+1] == null)
                                        HighlightBySelectedTileOffset(2, 1);
                                }

                                if (CheckBounds(selectedTileX + 2, selectedTileY - 1,
                                        0, 8, 0, 8)) {
                                    if (board[selectedTileX+2][selectedTileY-1] == null)
                                        HighlightBySelectedTileOffset(2, -1);
                                }

                                if (CheckBounds(selectedTileX - 2, selectedTileY + 1,
                                        0, 8, 0, 8)) {
                                    if (board[selectedTileX-2][selectedTileY+1] == null)
                                        HighlightBySelectedTileOffset(-2, 1);
                                }

                                if (CheckBounds(selectedTileX - 2, selectedTileY - 1,
                                        0, 8, 0, 8)) {
                                    if (board[selectedTileX-2][selectedTileY-1] == null)
                                        HighlightBySelectedTileOffset(-2, -1);
                                }

                                break;
                            case KING:
                                DrawText("W KING", 600, 380);
                                break;
                            case QUEEN:
                                DrawText("W QUEEN", 600, 380);
                                break;
                            case ROOK:
                                DrawText("W ROOK", 600, 380);
                                for (int i = 0; i < 8; i++ ) {
                                        if (board[selectedTileX][i] == null) {
                                            HighlightBySelectedTileOffset(0, i);
                                        }
                                        if (board[i][selectedTileY] == null) {
                                            HighlightBySelectedTileOffset(i, 0);
                                        }
                                }
                                break;
                            case PAWN:
                                HighlightBySelectedTileOffset(0, 1);
                                DrawText("W PAWN", 600, 380);
                                if (selectedTileY == 1) {
                                        HighlightBySelectedTileOffset(0, 2);
                                }
                                break;
                            default:
                                break;
                        }

                    }
                    else if(board[selectedTileX][selectedTileY].team == ChessPiece.Team.BLACK) {
                        switch (board[selectedTileX][selectedTileY].type) {
                            case BISHOP:
                                DrawText("B BISHOP", 600, 380);
                                break;
                            case KNIGHT:
                                DrawText("B KNIGHT", 600, 380);
                                break;
                            case KING:
                                DrawText("B KING", 600, 380);
                                break;
                            case QUEEN:
                                DrawText("B QUEEN", 600, 380);
                                break;
                            case ROOK:
                                DrawText("B ROOK", 600, 380);
                                break;
                            case PAWN:
                                DrawText("B PAWN", 600, 380);
                                break;
                            default:
                                break;
                            }
                    }
                }
                else {
                    if (board[selectedTileX][selectedTileY].team == ChessPiece.Team.WHITE) {
                        switch (board[selectedTileX][selectedTileY].type) {
                            case BISHOP:
                                DrawText("W BISHOP", 600, 380);
                                break;
                            case KNIGHT:
                                DrawText("W KNIGHT", 600, 380);
                                break;
                            case KING:
                                DrawText("W KING", 600, 380);
                                break;
                            case QUEEN:
                                DrawText("W QUEEN", 600, 380);
                                break;
                            case ROOK:
                                DrawText("W ROOK", 600, 380);
                                break;
                            case PAWN:
                                DrawText("W PAWN", 600, 380);
                                break;
                            default:
                                break;
                        }

                    }
                    else if(board[selectedTileX][selectedTileY].team == ChessPiece.Team.BLACK) {
                        switch (board[selectedTileX][selectedTileY].type) {
                            case BISHOP:
                                DrawText("B BISHOP", 600, 380);
                                break;
                            case KNIGHT:
                                DrawText("B KNIGHT", 600, 380);
                                break;
                            case KING:
                                DrawText("B KING", 600, 380);
                                break;
                            case QUEEN:
                                DrawText("B QUEEN", 600, 380);
                                break;
                            case ROOK:
                                DrawText("B ROOK", 600, 380);
                                break;
                            case PAWN:
                                HighlightBySelectedTileOffset(0, -1);
                                DrawText("B PAWN", 600, 380);
                                if (selectedTileY == 6) {
                                    HighlightBySelectedTileOffset(0, -2);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }


            }
            String str = ConvertIndexToString(selectedTileX, selectedTileY);
            DrawText("Selected: " + str, 600, 350);
        }
        game.batch.end();

        game.pieceBatch.begin();
        DrawChessPieces();
        game.pieceBatch.end();
    }

    @Override
    public void resize(int x, int y) {

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
