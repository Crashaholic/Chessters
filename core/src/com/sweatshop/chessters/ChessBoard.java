package com.sweatshop.chessters;



public class ChessBoard
{
    public ChessPiece[] board;

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

}
