package com.sweatshop.chessters;

public class ChessPiece
{
    public enum Team
    {
        WHITE,
        BLACK
    }

    public enum Piece_Type
    {
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        KING,
        QUEEN
    }

    public Team team;
    public Piece_Type type;
    public ChessPiece(Team team, Piece_Type type)
    {
        super();
        this.team = team;
        this.type = type;
    }

    public String GetSpriteName()
    {
        return ((team == Team.WHITE) ? "w" : "b") + type.toString().toLowerCase();
    }

}
