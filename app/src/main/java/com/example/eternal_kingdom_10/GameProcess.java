package com.example.eternal_kingdom_10;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GameProcess extends AppCompatActivity {

    int mood, relig, army, cash,nomer = 0,year=475,CardId=1;
    //Bitmap pic;
    Canvas canvas;
    Paint paint;
    Card card;
    String name = "Генрих";
    CardDataBase DataBase;
    TreeMap<Integer, Card> map;
    TreeMap<Integer, Card> deathmap;
    List<Card> list = new ArrayList<>();
    TreeMap<Integer, Card> map1 = new TreeMap<>();
    TreeMap<Integer, Card> map2 = new TreeMap<>();



    public GameProcess(CardDataBase DataBase) {
        this.DataBase = DataBase;
        //this.width = width;
        //this.height = height;
        this.mood = 50;
        this.relig = 50;
        this.army = 50;
        this.cash = 50;
        cardCreation();
        while ((map==null) || (deathmap==null)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        updateCard(0);
        }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public double getRandomInt(double min, double max) {  //[min; max)
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min)) + min;
    }

    public void updateCard(int x) {
        if(x==0) {
            do {
                nomer = (int) getRandomInt(1, 9 + 1);
                card = map.get(nomer);
            } while (card == null);
            if (map.size() > 1) {
                map.remove(nomer);
            }
        } else {
            card = deathmap.get(x);
        }
    }

    public void DrawCard() {
        paint.setColor(Color.GRAY);
        canvas.drawRect(50, 1730, 520, 1850, paint);              //прямоугольники на ответы
        canvas.drawRect(560, 1730, 1030, 1850, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(50);                                                         //карта
        card.writeAnsw1(canvas, paint);
        card.writeAnsw2(canvas, paint);
        card.writeTaskp1(canvas, paint);
        card.writeTaskp2(canvas, paint);
        paint.setTextSize(60);
        card.writePost(canvas, paint);

        canvas.drawRect(540-390,400,540+390,1350,paint);          //картинка

        //canvas.drawLine(540,0,540,2000,paint);                 //полоса(центр)

        canvas.drawText(Integer.toString(mood), 160, 50 ,paint);                 //статы
        canvas.drawText(Integer.toString(army), 208*2, 50 ,paint);
        canvas.drawText(Integer.toString(cash), 210*3, 50 ,paint);
        canvas.drawText(Integer.toString(relig), 216*4, 50 ,paint);
        canvas.drawText("Народ", 100, 120 ,paint);
        canvas.drawText("Армия", 180*2, 120 ,paint);
        canvas.drawText("Казна", 194*3, 120 ,paint);
        canvas.drawText("Религия", 200*4, 120 ,paint);

        canvas.drawText("Год:", 380,1970,paint);                             //год
        canvas.drawText(Integer.toString(year), 540,1970,paint);

        canvas.drawText(name, 400, 2050,paint);
    }

    public void DrawPoints(int x){
        if(x==1){
            if(card.mood1!=0){
                paint.setStrokeWidth(100);
                canvas.drawCircle(200, 160, 10,paint);
            }
            if(card.army1!=0){
                paint.setStrokeWidth(100);
                canvas.drawCircle(200+256,160,10,paint);
            }
            if(card.cash1!=0){
                paint.setStrokeWidth(100);
                canvas.drawCircle(155+256*2,160,10,paint);
            }
            if(card.relig1!=0){
                paint.setStrokeWidth(100);
                canvas.drawCircle(138+256*3,160,10,paint);
            }
        } else if(x==2){
            if(card.mood2!=0){
                paint.setStrokeWidth(100);
                canvas.drawCircle(200,160,10,paint);
            }
            if(card.army2!=0){
                paint.setStrokeWidth(100);
                canvas.drawCircle(200+256,160,10,paint);
            }
            if(card.cash2!=0){
                paint.setStrokeWidth(100);
                canvas.drawCircle(155+256*2,160,10,paint);
            }
            if(card.relig2!=0){
                paint.setStrokeWidth(100);
                canvas.drawCircle(138+256*3,160,10,paint);
            }
        }
    }

    public void setMood(int x){
        if(x==1){
            this.mood = this.mood+card.mood1;
        } else {
            this.mood = this.mood+card.mood2;
        }
    }

    public void setArmy(int x){
        if(x==1){
            this.army = this.army+card.army1;
        } else {
            this.army = this.army+card.army2;
        }
    }

    public void setCash(int x){
        if(x==1){
            this.cash = this.cash+card.cash1;
        } else {
            this.cash = this.cash+card.cash2;
        }
    }

    public void setRelig(int x){
        if(x==1){
            this.relig = this.relig+card.relig1;
        } else {
            this.relig = this.relig+card.relig2;
        }
    }
    public void death(int x){
        updateCard(x);
    }

    public void Update(){
        this.mood = 50;
        this.relig = 50;
        this.army = 50;
        this.cash = 50;
        map = map1;
        deathmap = map2;
        updateCard(0);
    }

    public void cardCreation() {

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        List<Card> cards = DataBase.CardDao().getAll();
                        for (int i = 0; i < cards.size(); i++) {
                            Card card = cards.get(i);
                            if(!card.isDead) {
                                map1.put(card.id, card);
                            } else {
                                map2.put(CardId,card);
                                CardId++;
                                }
                        }
                        map = map1;
                        deathmap = map2;
                    }
                    catch (Exception e){
                        Log.e("cardCreation", e.toString());
                    }
                }
            });
            thread.start();
    }
}
