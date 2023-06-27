package org.example.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class Message {
    public User sender;
    public String text;
    public boolean isSeen;
    public String time;
    public ArrayList<User> getters;
    public boolean isNotDeleted;
    public boolean isNotDeletedForSelf;

    private void setTime(){
        long unixTime = System.currentTimeMillis() / 1000L;
        Date time=new java.util.Date((long)unixTime*1000);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        Formatter formatter  = new Formatter();
        this.time = String.valueOf(formatter.format("%02d:%02d",hours,minutes));
    }
}
