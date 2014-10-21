package com.snakeremake.main;

/**
 * Created by mcat on 21/10/14.
 */
public class PixelData {

    private final String id;
    private final PixelType type;
    private final int textureID;

    public PixelData(String id, PixelType type){
        this(id,type,0);
    }
    public PixelData(String id, PixelType type, int textureID){

        this.id = id;
        this.type = type;
        this.textureID = textureID;
    }
}
