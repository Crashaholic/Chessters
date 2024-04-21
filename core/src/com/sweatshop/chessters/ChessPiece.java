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
    public boolean hasMoved;
    public ChessPiece(Team team, Piece_Type type)
    {
        this.team = team;
        this.type = type;
        this.hasMoved = false;
    }
}
