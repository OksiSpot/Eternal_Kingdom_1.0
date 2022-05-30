package com.example.eternal_kingdom_10;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public String post, taskp1, taskp2, answ1, answ2;
    public int mood1, relig1, army1, cash1;
    public int mood2, relig2, army2, cash2;
    public boolean isDead;
    //Bitmap pic;

    public Card(boolean isDead, String post, String taskp1, String taskp2, String answ1, String answ2,
                int mood1, int relig1, int army1, int cash1,
                int mood2, int relig2, int army2, int cash2
                //Bitmap pic
    ) {
        this.isDead = isDead;
        this.post = post;
        this.taskp1 = taskp1;
        this.taskp2 = taskp2;
        this.answ1 = answ1;
        this.answ2 = answ2;
        this.mood1 = mood1;
        this.relig1 = relig1;
        this.army1 = army1;
        this.cash1 = cash1;
        this.mood2 = mood2;
        this.relig2 = relig2;
        this.army2 = army2;
        this.cash2 = cash2;
        //this.pic = pic;
    }

    /* public void drawPic(Canvas canvas){
         Paint paint = new Paint();
         canvas.drawBitmap(pic, 0, 0, paint);
     }*/
    public void writePost(Canvas canvas, Paint paint) {
        canvas.drawText(post, 410, 300, paint);
    }

    public void writeTaskp1(Canvas canvas, Paint paint) {
        canvas.drawText(taskp1, 160, 1500, paint);
    }

    public void writeTaskp2(Canvas canvas, Paint paint) {
        canvas.drawText(taskp2, 200, 1600, paint);
    }

    public void writeAnsw1(Canvas canvas, Paint paint) {
        canvas.drawText(answ1, 80, 1800, paint);
    }

    public void writeAnsw2(Canvas canvas, Paint paint) {
        canvas.drawText(answ2, 600, 1800, paint);
    }

}
