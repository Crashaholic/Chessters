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
    private final Texture checkTile;
    private final Texture slectTile;
    private final Texture whiteTile;
    private final Texture blackTile;
    private final Texture hlghtTile2;
    private final Texture checkTile2;

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
    private final Texture sideboardTexture;
    private final Texture logoTexture;
    private final Texture nextTurnIconTexture;
    private final Texture resetIconTexture;

    private ChessPiece.Team currentTurn;

    private int selectedTileX;
    private int selectedTileY;

    private final ChessTiming globalTimer;
    private final ChessTiming whiteTimer;
    private final ChessTiming blackTimer;

    private boolean gameStarted;

    private final String[] gameMoveHistory;
    private int gameMovesTurnCount;

    private int enPassantX;
    private int enPassantY;

    private boolean king_QueensideCastle = true;
    private boolean king_KingsideCastle = true;

    OrthographicCamera camera;
    FitViewport fitViewport;

    public enum TileStatus
    {
        OUT_OF_BOUNDS,
        EMPTY,
        HAS_ALLY,
        HAS_ENEMY
    }

    public ChessBoardScreen(final ChesstersGame game)
    {
        this.game = game;

        globalTimer = new ChessTiming();
        whiteTimer = new ChessTiming();
        blackTimer = new ChessTiming();

        camera = new OrthographicCamera();
        fitViewport = new FitViewport(600, 600);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        board = new ChessPiece[8][8];
        gameMoveHistory = new String[10];

        hlghtTile = new Texture(Gdx.files.internal("htile.png"));
        checkTile = new Texture(Gdx.files.internal("ctile.png"));
        slectTile = new Texture(Gdx.files.internal("stile.png"));
        whiteTile = new Texture(Gdx.files.internal("wtile.png"));
        blackTile = new Texture(Gdx.files.internal("btile.png"));
        hlghtTile2= new Texture(Gdx.files.internal("htile2.png"));
        checkTile2= new Texture(Gdx.files.internal("ctile2.png"));

        hlghtTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        checkTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        slectTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        whiteTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        blackTile.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        hlghtTile2.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        checkTile2.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

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

        sideboardTexture = new Texture(Gdx.files.internal("sideboard.png"));
        sideboardTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        logoTexture = new Texture(Gdx.files.internal("logo.png"));

        nextTurnIconTexture = new Texture(Gdx.files.internal("nextTurn.png"));
        resetIconTexture = new Texture(Gdx.files.internal("reset.png"));

        nextTurnIconTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        resetIconTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
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

    public boolean ClickedTile(Vector3 mousePos, int tileX, int tileY)
    {
        if (CheckBounds(mousePos.x, mousePos.y,
                tileX * 75, (tileX + 1) * 75 , tileY * 75, (tileY + 1) * 75))
        {
            return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
        }
        return false;
    }

    public void ResetSelection()
    {
        selectedTileX = selectedTileY = -1;
    }

    public ChessPiece GetPieceAtTile(int x, int y)
    {
        /*ensure i dont check out of bounds*/
        if (CheckBounds(x, y, 0, 8, 0, 8))
            return board[x][y];
        return null;
    }

    public TileStatus GetTileStatus(int x, int y)
    {
        ChessPiece pieceAtTile = GetPieceAtTile(x, y);
        if (CheckBounds(x, y,0, 8, 0, 8))
        {
            if (pieceAtTile == null)
                return TileStatus.EMPTY;
            else if (pieceAtTile.team == currentTurn)
                return TileStatus.HAS_ALLY;
            else return TileStatus.HAS_ENEMY;
        }
        return TileStatus.OUT_OF_BOUNDS;
    }

    public boolean ClickedHighlightedTile(Vector3 mousePos, int tileX, int tileY)
    {
        if (!CheckBounds(tileX, tileY, 0, 8, 0, 8))
            return false;
        HighlightTile(tileX, tileY);
        return ClickedTile(mousePos, tileX, tileY);
    }

    public void PushToHistory(String move)
    {
        for (int i = 9; i >= 1; --i)
        {
            gameMoveHistory[i] = gameMoveHistory[i - 1];
        }
        gameMoveHistory[0] = move;
    }

    // to change from index to string, use to print
    public String ConvertIndexToString(int x, int y)
    {
        char xComponent = (char)('a' + x);
        y += 1;
        return xComponent + String.valueOf(y);
    }

    public void Move(int fromX, int fromY, int toX, int toY)
    {
        String move = "";
        ChessPiece.Piece_Type t = board[fromX][fromY].type;
        switch(t)
        {
            case PAWN:
                move += "";
                break;
            case ROOK:
                move += "R";
                break;
            case QUEEN:
                move += "Q";
                break;
            case KING:
                move += "K";
                break;
            case KNIGHT:
                move += "N";
                break;
            case BISHOP:
                move += "B";
                break;
            default:
                // should never land here EVER, but handled just in case
                break;
        }
        boolean isCapture = GetTileStatus(toX, toY) == TileStatus.HAS_ENEMY;
        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;
        board[toX][toY].hasMoved = true;
        if (isCapture)
        {
            if (t == ChessPiece.Piece_Type.PAWN)
            {
                move += ConvertIndexToString(fromX, fromY).charAt(0);
            }
            move += "x";
        }
        move += ConvertIndexToString(toX, toY);
        if (currentTurn == ChessPiece.Team.WHITE)
        {
            if (!gameStarted)
            {
                gameStarted = true;
            }
            PushToHistory(gameMovesTurnCount + ": " + move);
            currentTurn = ChessPiece.Team.BLACK;
        }
        else
        {
            gameMoveHistory[0] += " " + move;
            gameMovesTurnCount++;
            currentTurn = ChessPiece.Team.WHITE;
        }
    }

    /*checks to see if king is in check when its in this tile
    * returns True IF BEING ON THE TILE WILL CHECK*/
    public boolean CheckCheck(int tileToCheckX, int tileToCheckY)
    {
        if (CheckBounds(tileToCheckX, tileToCheckY, 0, 8, 0 ,8))
        {

        }
        return false;
    }

    public boolean CheckCheckmate(int tileToCheckX, int tileToCheckY)
    {
        return  CheckCheck(tileToCheckX - 1, tileToCheckY + 1) ||
                CheckCheck(tileToCheckX    , tileToCheckY + 1)||
                CheckCheck(tileToCheckX + 1, tileToCheckY + 1)||
                CheckCheck(tileToCheckX - 1, tileToCheckY)||
                CheckCheck(tileToCheckX    , tileToCheckY)||
                CheckCheck(tileToCheckX + 1, tileToCheckY)||
                CheckCheck(tileToCheckX + 1, tileToCheckY - 1)||
                CheckCheck(tileToCheckX, tileToCheckY - 1)||
                CheckCheck(tileToCheckX - 1, tileToCheckY - 1);
    }

    //meant *specifically* for en passant
    public void ExecuteEnPassant(int fromX, int fromY, int toX, int toY)
    {
        String move = "";
        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;
        board[toX][toY - ((currentTurn == ChessPiece.Team.WHITE) ? 1 : -1)] = null;
        board[toX][toY].hasMoved = true;
        move += ConvertIndexToString(fromX, fromY).charAt(0);
        move += "x";
        move += ConvertIndexToString(toX, toY);
        if (currentTurn == ChessPiece.Team.WHITE)
        {
            PushToHistory(gameMovesTurnCount + ": " + move + " e.p.");
            currentTurn = ChessPiece.Team.BLACK;
        }
        else
        {
            gameMoveHistory[0] += " " + move + " e.p.";
            gameMovesTurnCount++;
            currentTurn = ChessPiece.Team.WHITE;
        }
    }

    public void ExecuteCastling(boolean queenSide)
    {
        String move = "";
        if (queenSide)
        {
            board[2][selectedTileY] = board[selectedTileX][selectedTileY];
            board[3][selectedTileY] = board[0][selectedTileY];
            board[2][selectedTileY].hasMoved = true;
            board[3][selectedTileY].hasMoved = true;
            board[selectedTileX][selectedTileY] = null;
            board[0][selectedTileY] = null;
            move += "0-0-0";
        }
        else
        {
            board[6][selectedTileY] = board[selectedTileX][selectedTileY];
            board[5][selectedTileY] = board[7][selectedTileY];
            board[6][selectedTileY].hasMoved = true;
            board[5][selectedTileY].hasMoved = true;
            board[selectedTileX][selectedTileY] = null;
            board[7][selectedTileY] = null;
            move += "0-0";
        }
        if (currentTurn == ChessPiece.Team.WHITE)
        {
            PushToHistory(gameMovesTurnCount + ": " + move);
            currentTurn = ChessPiece.Team.BLACK;
        }
        else
        {
            gameMoveHistory[0] += " " + move + " e.p.";
            gameMovesTurnCount++;
            currentTurn = ChessPiece.Team.WHITE;
        }
    }
    
    public void Setup()
    {
        globalTimer.Set(0.0f);
        whiteTimer.Set(300.f);
        blackTimer.Set(300.f);
        gameStarted = false;

        for (int i = 0; i < 10; i++)
        {
            gameMoveHistory[i] = "";
        }

        gameMovesTurnCount = 1;

        currentTurn = ChessPiece.Team.WHITE;
        selectedTileX = selectedTileY = -1;
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                board[x][y] = null;
            }
        }

        enPassantX = enPassantY = -1;

        board[0][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.ROOK);
        board[1][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.KNIGHT);
        board[2][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.BISHOP);
        board[3][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.QUEEN);
        board[4][0] = new ChessPiece(ChessPiece.Team.WHITE, ChessPiece.Piece_Type.KING);
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
        board[3][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.QUEEN);
        board[4][7] = new ChessPiece(ChessPiece.Team.BLACK, ChessPiece.Piece_Type.KING);
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
        if ((x + y) % 2 == 1)
            game.batch.draw(hlghtTile, x * 75, y * 75, 75, 75);
        else
            game.batch.draw(hlghtTile2, x * 75, y * 75, 75, 75);
    }

    @Override
    public void show()
    {
        Setup();
    }

    private boolean CheckValidMovesInDirection(Vector3 mousePos, int x, int y, int dx, int dy) {
        boolean hasEnemy = false;
        boolean hasMoved = false;
        for (int i = 1; i < 8; i++) {
            int newX = x + i * dx;
            int newY = y + i * dy;
            TileStatus ts = GetTileStatus(newX, newY);
            if (ts == TileStatus.OUT_OF_BOUNDS || ts == TileStatus.HAS_ALLY) {
                break;
            } else {
                if (ts == TileStatus.HAS_ENEMY) {
                    hasEnemy = true;
                }
                if (ClickedHighlightedTile(mousePos, newX, newY)) {
                    Move(x, y, newX, newY);
                    hasMoved = true;
                }
                if (hasEnemy) {
                    break;
                }
            }
        }
        return hasMoved;
    }

    public void DrawPiece(Texture t, float x, float y)
    {
        game.pieceBatch.draw(t, x + 7.5f, y + 7.5f, 60, 60);
    }

    public void DrawChessPieces()
    {
        game.pieceBatch.begin();
        for (int x = 0; x < 8; ++x)
        {
            for (int y = 0; y < 8; ++y)
            {
                if (board[x][y] != null)
                {
                    Texture pieceToDraw = null;
                    if (board[x][y].team == ChessPiece.Team.WHITE)
                    {
                        switch (board[x][y].type)
                        {
                            case BISHOP:
                                pieceToDraw = whiteBish;
                                break;
                            case KNIGHT:
                                pieceToDraw = whiteKngt;
                                break;
                            case KING:
                                pieceToDraw = whiteKing;
                                break;
                            case QUEEN:
                                pieceToDraw = whiteQuen;
                                break;
                            case ROOK:
                                pieceToDraw = whiteRook;
                                break;
                            case PAWN:
                                pieceToDraw = whitePawn;
                                break;
                        }
                    }
                    else
                    {
                        switch (board[x][y].type) {
                            case BISHOP:
                                pieceToDraw = blackBish;
                                break;
                            case KNIGHT:
                                pieceToDraw = blackKngt;
                                break;
                            case KING:
                                pieceToDraw = blackKing;
                                break;
                            case QUEEN:
                                pieceToDraw = blackQuen;
                                break;
                            case ROOK:
                                pieceToDraw = blackRook;
                                break;
                            case PAWN:
                                pieceToDraw = blackPawn;
                                break;
                        }
                    }
                    DrawPiece(pieceToDraw, x * 75, y * 75);
                }
            }
        }
        game.pieceBatch.end();
    }

    public void DrawSidebar()
    {
        game.batch.begin();
        game.batch.draw(sideboardTexture, 600, 0, 200, 600);
        game.batch.draw(logoTexture, 600, 550, 200, 30);
        game.font.setColor(Color.BLACK);
        String str = "";
        if (selectedTileX >= 0 && selectedTileY >= 0)
        {
            str = ConvertIndexToString(selectedTileX, selectedTileY);
            if (board[selectedTileX][selectedTileY] != null)
            {
                String team = board[selectedTileX][selectedTileY].team.toString();
                String type = board[selectedTileX][selectedTileY].type.toString();
                DrawText(team + " " + type, 620, 515);
            }
        }
        DrawText("Current Selected: " + str, 620, 530);
        DrawText("Game Timer: " + globalTimer.GetMinutesString() + ":" + globalTimer.GetSecondsString(), 630, 550);
        DrawText("White Timer: " + whiteTimer.GetMinutesString() + ":" + whiteTimer.GetSecondsString(), 630, 225);
        DrawText("Black Timer: " + blackTimer.GetMinutesString() + ":" + blackTimer.GetSecondsString(), 630, 405);
        Vector3 scale = new Vector3(game.font.getData().scaleX, game.font.getData().scaleY, 0);
        game.font.getData().setScale(scale.x + 1, scale.y + 1);
        if (currentTurn == ChessPiece.Team.WHITE)
        {
            game.font.setColor(Color.WHITE);
            DrawText("White's Turn", 610, 205);
        }
        else
        {
            game.font.setColor(Color.BLACK);
            DrawText("Black's Turn", 615, 435);
        }
        game.font.getData().setScale(scale.x, scale.y);
        game.font.setColor(Color.BLACK);
        DrawText("Kingside Castling avail: " + king_KingsideCastle, 600, 185);
        DrawText("Queenside Castling avail: " + king_QueensideCastle, 600, 197);

        for (int i = 0; i < 10; i++)
        {
            if (gameMoveHistory[i] != null)
                DrawText(gameMoveHistory[i], 630, 250 + i * 15);
        }
        game.font.setColor(Color.WHITE);
        for (int y = 0; y < 8; ++y)
        {
            for (int x = 0; x < 8; ++x)
            {
                DrawText("" + (char)('a' + (char)x), (x * 75.f) + 60.5f, 12);
            }
            DrawText("" + (y + 1), 3, (y * 75.f) + 70.5f);
        }
        game.font.setColor(Color.BLACK);
        game.batch.end();
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
                whiteTimer.Decrement(dt);
            else
                blackTimer.Decrement(dt);
            globalTimer.Increment(dt);
        }

        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

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
                    if ((x + y) % 2 == 1)
                        game.batch.draw(whiteTile, x * 75, y * 75, 75, 75);
                    else
                        game.batch.draw(blackTile, x * 75, y * 75, 75, 75);
                }
                // if the click pos is within this tile's boundary.
                // boundary is current tile's x to next tile's x
                // and current tile's y to next tile's y
                if (CheckBounds(mousePos.x, mousePos.y,
                        x * 75, (x + 1) * 75, y * 75, (y + 1) * 75))
                {
                    if (selectedTileX != x || selectedTileY != y)
                    {
                        //check if tile has a piece
                        if (board[x][y] != null) {
                            // check if piece is of the same team as current team
                            if (currentTurn == ChessPiece.Team.WHITE && board[x][y].team == ChessPiece.Team.WHITE
                            ||  currentTurn == ChessPiece.Team.BLACK && board[x][y].team == ChessPiece.Team.BLACK)
                            {
                                // highlight the tile if it is
                                game.batch.draw(hlghtTile, x * 75, y * 75, 75, 75);
                                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
                                {
                                    // select that tile if the player clicked
                                    selectedTileX = x;
                                    selectedTileY = y;
                                }
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
        game.batch.end();

        game.batch.begin();
        if (selectedTileX >= 0 && selectedTileY >= 0 && board[selectedTileX][selectedTileY] != null) {
            boolean hasMoved = false;
            @SuppressWarnings("unused")
            boolean enPassantJustOpened = false;
            switch (board[selectedTileX][selectedTileY].type) {
                case BISHOP:
                    hasMoved = CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 1, 1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, -1, 1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 1, -1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, -1, -1);
                    break;
                case KNIGHT:
                    int[][] knightMoves = {{1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
                    for (int[] move : knightMoves)
                    {
                        if (CheckBounds(selectedTileX + move[0], selectedTileY + move[1],0, 8, 0, 8))
                        {
                            TileStatus ts = GetTileStatus(selectedTileX + move[0], selectedTileY + move[1]);
                            if (ts == TileStatus.EMPTY || ts == TileStatus.HAS_ENEMY)
                            {
                                if (ClickedHighlightedTile(mousePos, selectedTileX + move[0], selectedTileY + move[1]))
                                {
                                    Move(selectedTileX, selectedTileY, selectedTileX + move[0], selectedTileY + move[1]);
                                    hasMoved = true;
                                    break; // Exit loop after the first valid move
                                }
                            }
                        }
                    }
                    break;
                case QUEEN:
                    hasMoved = CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 1, 1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, -1, 1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 1, -1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, -1, -1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 0, 1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 0, -1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 1, 0) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, -1, 0);
                    break;
                case ROOK:
                    hasMoved = CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 0, 1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 0, -1) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, 1, 0) ||
                            CheckValidMovesInDirection(mousePos, selectedTileX, selectedTileY, -1, 0);
                    break;
                case KING:
                    int[][] kingMoves = {{0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}};
                    for (int[] move : kingMoves)
                    {
                        if (CheckBounds(selectedTileX + move[0], selectedTileY + move[1],0, 8, 0, 8))
                        {
                            TileStatus ts = GetTileStatus(selectedTileX + move[0], selectedTileY + move[1]);
                            if (ts == TileStatus.EMPTY || ts == TileStatus.HAS_ENEMY)
                            {
                                if (ClickedHighlightedTile(mousePos, selectedTileX + move[0], selectedTileY + move[1]))
                                {
                                    Move(selectedTileX, selectedTileY, selectedTileX + move[0], selectedTileY + move[1]);
                                    hasMoved = true;
                                    break; // Exit loop after the first valid move
                                }
                            }
                        }
                    }
                    if (!hasMoved)
                    {
                        king_KingsideCastle = true;
                        king_QueensideCastle = true;
                        if (GetPieceAtTile(selectedTileX, selectedTileY).hasMoved)
                        {
                            king_KingsideCastle = king_QueensideCastle = false;
                        }
                        else
                        {
                            for (int i = 0; i < 8; ++i)
                            {
                                // queenside castling
                                ChessPiece pieceAtTile = GetPieceAtTile(i, selectedTileY);
                                // if there are pieces between the king and the queenside rook
                                if (pieceAtTile != null)
                                {
                                    if (pieceAtTile.type == ChessPiece.Piece_Type.ROOK)
                                    {
                                        if (pieceAtTile.hasMoved)
                                        {
                                            // Queenside rook has moved, disable queenside castling
                                            if (i < selectedTileX)
                                            {
                                                king_QueensideCastle = false;
                                                i = selectedTileX;
                                            }
                                            else if (i > selectedTileX)
                                            {
                                                king_KingsideCastle = false;
                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        // Piece other than rook found, disable castling if it's on the king's path
                                        if (i < selectedTileX)
                                        {
                                            king_QueensideCastle = false;
                                            i = selectedTileX;
                                        }
                                        else if (i > selectedTileX)
                                        {
                                            king_KingsideCastle = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (king_QueensideCastle)
                            {
                                if (!CheckCheck(1, selectedTileY))
                                {
                                    if (ClickedHighlightedTile(mousePos, 2, selectedTileY))
                                    {
                                        ExecuteCastling(true);
                                        hasMoved = true;
                                    }
                                }
                            }

                            if (king_KingsideCastle)
                            {
                                if (!CheckCheck(6, selectedTileY))
                                {
                                    if (ClickedHighlightedTile(mousePos, 6, selectedTileY))
                                    {
                                        ExecuteCastling(false);
                                        hasMoved = true;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case PAWN:
                    int direction = (currentTurn == ChessPiece.Team.WHITE) ? 1 : -1; // Determine the direction of pawn movement based on the current turn
                    // Check if the pawn is at its starting position and can move two squares forward
                    if ((selectedTileY == 1 && direction == 1) || (selectedTileY == 6 && direction == -1)) {
                        TileStatus ts = GetTileStatus(selectedTileX, selectedTileY + direction);
                        if (ts == TileStatus.EMPTY) {
                            ts = GetTileStatus(selectedTileX, selectedTileY + direction * 2);
                            if (ts == TileStatus.EMPTY) {
                                if (ClickedHighlightedTile(mousePos, selectedTileX, selectedTileY + 2 * direction)) {
                                    Move(selectedTileX, selectedTileY, selectedTileX, selectedTileY + 2 * direction);
                                    enPassantJustOpened = true;
                                    enPassantX = selectedTileX;
                                    enPassantY = selectedTileY + direction;
                                    hasMoved = true;
                                    break;
                                }
                            }
                        }
                    }

                    // Check for regular pawn move one square forward
                    TileStatus ts = GetTileStatus(selectedTileX, selectedTileY + direction);
                    if (ts == TileStatus.EMPTY) {
                        if (ClickedHighlightedTile(mousePos, selectedTileX, selectedTileY + direction)) {
                            Move(selectedTileX, selectedTileY, selectedTileX, selectedTileY + direction);
                            hasMoved = true;
                            break;
                        }
                    }

                    if (selectedTileY + direction == enPassantY && (selectedTileX - 1 == enPassantX || selectedTileX + 1 == enPassantX)) {
                        // Check if the move is a capturing move
                        if (ClickedHighlightedTile(mousePos, enPassantX, enPassantY)) {
                            // Execute en passant capture
                            ExecuteEnPassant(selectedTileX, selectedTileY, enPassantX, enPassantY);
                            hasMoved = true;
                            break;
                        }
                    }

                    // Check for diagonal captures
                    TileStatus leftDiagTs = GetTileStatus(selectedTileX - 1, selectedTileY + direction);
                    TileStatus rightDiagTs = GetTileStatus(selectedTileX + 1, selectedTileY + direction);

                    if (leftDiagTs == TileStatus.HAS_ENEMY) {
                        if (ClickedHighlightedTile(mousePos, selectedTileX - 1, selectedTileY + direction)) {
                            Move(selectedTileX, selectedTileY, selectedTileX - 1, selectedTileY + direction);
                            hasMoved = true;
                            break;
                        }
                    }

                    if (rightDiagTs == TileStatus.HAS_ENEMY) {
                        if (ClickedHighlightedTile(mousePos, selectedTileX + 1, selectedTileY + direction)) {
                            Move(selectedTileX, selectedTileY, selectedTileX + 1, selectedTileY + direction);
                            hasMoved = true;
                        }
                    }
                    break;
                default:
                    break;
            }
            if (hasMoved) {
                ResetSelection();
                if (enPassantJustOpened)
                    enPassantJustOpened = false;
                else
                    enPassantX = enPassantY = -1;
            }
        }
        game.batch.end();

        DrawChessPieces();
        DrawSidebar();
        game.batch.begin();
        if (Button(mousePos, "", 75 * 8 + 30, 30, 50, 50))
        {
            Gdx.app.log("Chessters", "Resetting!");
            Setup();
        }
        game.batch.draw(resetIconTexture, 75 * 8 + 40, 40, 30, 30);
        game.batch.end();
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
        buttonTexture.dispose();
        sideboardTexture.dispose();
        nextTurnIconTexture.dispose();
        resetIconTexture.dispose();
        hlghtTile2.dispose();
        checkTile2.dispose();

        hlghtTile.dispose();
        checkTile.dispose();
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
