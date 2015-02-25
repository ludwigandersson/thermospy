package com.luan.thermospy.android.core.pojo;

public enum AspectRatio {
    ASPECT_RATIO_UNKNOWN(0,0,0),
    ASPECT_RATIO_1_1(1,1,1),
    ASPECT_RATIO_2_1(2,2,1),
    ASPECT_RATIO_4_3(3,4,3);

    private final int x;
    private final int y;
    private int id;
    AspectRatio(int id, int width, int height)
    {
        this.id = id;
        this.x = width;
        this.y = height;
    }

    public int getId()
    {
        return id;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public static AspectRatio parse(String string)
    {
        switch(string) {
            case "1":
                return ASPECT_RATIO_1_1;
            case "2":
                return ASPECT_RATIO_2_1;
            case "3":
                return ASPECT_RATIO_4_3;
            default:
                return ASPECT_RATIO_UNKNOWN;
        }
    }

    public static AspectRatio fromInt(int id)
    {
        switch (id)
        {
            case 1:
                return ASPECT_RATIO_1_1;
            case 2:
                return ASPECT_RATIO_2_1;
            case 3:
                return ASPECT_RATIO_4_3;
            default:
                return ASPECT_RATIO_UNKNOWN;

        }
    }
}
