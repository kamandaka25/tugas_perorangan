package id.sch.smktelkom_mlg.privateassignment.xirpl236.individu;

/**
 * Created by Kamandaka on 6/12/2017.
 */

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Noteview implements Serializable {
    private Date date;
    private String text;
    private boolean fullDisplayed;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");

    public Noteview(){
        this.date = new Date();
    }
    public Noteview(long time, String text){
        this.date = new Date(time);
        this.text = text;
    }
    public String getDate(){
        return dateFormat.format(date);
    }
    public long getTime(){
        return date.getTime();
    }
    public void setTime(long time) {
        this.date = new Date(time);
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
    public String getShortText() {
        String temp = text.replaceAll("\n", " ");
        if (temp.length() > 25) {
            return temp.substring(0, 25) + "...";
        } else {
            return temp;
        }
    }
    public void setFullDisplayed(boolean fullDisplayed) {
        this.fullDisplayed = fullDisplayed;
    }
    public boolean isFullDisplayed() {
        return this.fullDisplayed;
    }
    @Override
    public String toString() {
        return this.text;
    }

}