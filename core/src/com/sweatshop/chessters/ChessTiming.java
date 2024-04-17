package com.sweatshop.chessters;

public class ChessTiming
{
    private float internalTime;

    public ChessTiming()
    {
        internalTime = 0.f;
    }

    public ChessTiming(float initial)
    {
        internalTime = initial;
    }

    public int GetSeconds()
    {
        return (int) internalTime % 60;
    }

    public int GetMinutes()
    {
        return (int) internalTime / 60;
    }

    public int GetAsSeconds()
    {
        return (int) internalTime;
    }

    public String GetSecondsString()
    {
        int seconds = this.GetSeconds();
        int seconds1 = seconds % 10;
        int seconds2 = seconds / 10;
        return "" + seconds2 + seconds1;
    }

    public String GetMinutesString()
    {
        int minutes = this.GetMinutes();
        int minutes1 = minutes % 10;
        int minutes2 = minutes / 10;
        return "" + minutes2 + minutes1;
    }

    public void Set(float seconds)
    {
        internalTime = seconds;
    }

    public void Increment(float dt)
    {
        internalTime += dt;
    }

    public void Decrement(float dt)
    {
        internalTime -= dt;
    }
}
