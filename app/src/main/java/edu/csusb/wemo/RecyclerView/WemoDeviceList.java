package edu.csusb.wemo.RecyclerView;

/**
 * Created by Luong Randy on 2/24/2017.
 */

public class WemoDeviceList {
    private String wemoName;
    private String wemoDescription;

    public String getWemoName(){return wemoName;}
    public String getWemoDescription() {return wemoDescription;}
    public void setWemoDescription() {this.wemoDescription = wemoDescription;}
    public void setWemoName(){this.wemoName = wemoName;}
    //TODO: the code below should not be needed when the app is complete
    public void setName(String meme) {
        wemoName = meme;
    }
    public void setDescription(String memes){
        wemoDescription = memes;
    }

}
