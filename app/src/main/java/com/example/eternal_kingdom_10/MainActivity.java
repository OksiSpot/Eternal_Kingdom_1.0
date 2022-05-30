package com.example.eternal_kingdom_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    Context context;
    ImageView imageView;
    float x, y;
    GameProcess GP;
    int touchPoints = 0;
    int gameStatus=1;
    int width;
    int height;
    boolean loading=true,isDead = false;
    private SharedPreferences prefs = null;
    private CardDataBase DataBase;
    ArrayList<Card> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        Data();
        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).apply();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    list.add(new Card(false,"Генерал", "Солдаты просят залатать дыры", "в крыше казарм", "Выделить средства", "Не выделять"
                            , 10, 0, 20, -10
                            , -20, 0, -20, 0));
                    list.add( new Card(false,"Генерал", "Пастор предлагает каждое утро", "проводить службу для солдат", "Отличная идея", "Пожалуй, нет"
                            , 10, 30, 10, -10
                            , 10, -10, 15, 0));
                    list.add( new Card(false,"Чиновник", "В городе чума!", "", "Помолимся", "Закрыть ворота"
                            , -15, 15, -20, -10
                            , -10, 0, -10, -5));
                    list.add( new Card(false,"Бард", "Можно исполнить вам песню?", "", "Да", "Нет"
                            , +5, 0, 0, 0
                            , 0, 0, 0, 0));
                    list.add( new Card(false,"Художник", "Я хочу написать Ваш портрет", "", "Валяй", "Нет"
                            ,10, 5, 0, -10
                            , -5, 0, 0, 0));
                    list.add( new Card(false,"Священник", "В лесу видели оборотней", "", "Очистите его", "Чушь"
                            , 10, 15, 0, -15
                            , -10, -10, 0, 0));
                    list.add( new Card(false,"Харольд", "Проведем совместную охоту?", "", "Да", "Нет"
                            , 0, 0, 10, -20
                            , -20, 0, 0, 0));
                    list.add( new Card(false,"Томаш", "В этом году большой урожай", "Поднять налог на хлеб?", "Немного", "Втрое"
                            , 0, 0, 0, 10
                            , -15, 0, 0, 30));
                    list.add( new Card(false,"Чиновник", "Обрушилась северная башня!", "", "Починить", "Упала и упала"
                            , -10, 0, 0, -20
                            , -20, 0, -15, 0));

                    //--------------------------------------------------------------------
                    list.add(new Card(true,"Бунт", "Народ недоволен вашим правлением!","Вас повесили на центральной площади","Заново","Выход",
                            0,0,0,0,0,0,0,0));
                    list.add(new Card(true,"Нападение", "Ваша армия слишком слаба","Вас поработил соседний правитель","Заново","Выход",
                            0,0,0,0,0,0,0,0));
                    list.add(new Card(true,"Казна обнищала", "В казне не осталось средств","Вас сместили ваши же купцы","Заново","Выход",
                            0,0,0,0,0,0,0,0));
                    list.add(new Card(true,"Недостаток веры", "У людей нет надежды","Таким народом бесполезно управлять","Заново","Выход",
                            0,0,0,0,0,0,0,0));
        /*list.add(new Card(true,"Бунт", "Народ недоволен вашим правлением!","Вас повесили на центральной площади","Заново","",
                0,0,0,0,0,0,0,0));
        list.add(new Card(true,"Бунт", "Народ недоволен вашим правлением!","Вас повесили на центральной площади","Заново","",
                0,0,0,0,0,0,0,0));
        list.add(new Card(true,"Бунт", "Народ недоволен вашим правлением!","Вас повесили на центральной площади","Заново","",
                0,0,0,0,0,0,0,0));
        list.add(new Card(true,"Бунт", "Народ недоволен вашим правлением!","Вас повесили на центральной площади","Заново","",
                0,0,0,0,0,0,0,0));*/

                    for(int i=0; i<list.size();i++) {
                        DataBase.CardDao().insert(list.get(i));
                    }
                    loading=false;
                }
            });
            thread.start();
        }
        else {loading=false;}

        while(loading) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setContentView(new MyDraw(this));
        //setContentView(R.layout.activity_main);

    }

    public class MyDraw extends View {
        public MyDraw(Context context) {
            super(context);
            GP = new GameProcess(DataBase);
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));

            canvas.drawColor(Color.BLACK);

            GP.setCanvas(canvas);
            GP.setPaint(paint);
            //GP.updateCard();
            GP.DrawCard();
            GP.DrawPoints(touchPoints);


        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            x = event.getX();
            y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (gameStatus != 0) {
                        if ((x >= 50 && x <= 520) && (y >= 1730 && y <= 1850)) {
                            touchPoints=1;
                        } else if ((x >= 560 && x <= 1030) && (y >= 1730 && y <= 1850)) {
                            touchPoints=2;
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (gameStatus != 0) {
                        if ((x >= 50 && x <= 520) && (y >= 1730 && y <= 1850)) {
                            touchPoints=1;
                        } else if ((x >= 560 && x <= 1030) && (y >= 1730 && y <= 1850)) {
                            touchPoints=2;
                        } else {
                            touchPoints = 0;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (gameStatus != 0) {
                        if ((x >= 50 && x <= 520) && (y >= 1730 && y <= 1850)) {
                            //GP.drawCanvas();
                            GP.setMood(1);
                            GP.setArmy(1);
                            GP.setCash(1);
                            GP.setRelig(1);
                            if ((GP.mood > 0) && (GP.cash > 0) && (GP.army > 0) && (GP.relig > 0)) {
                                GP.year++;
                                GP.updateCard(0);
                            } else {
                                gameStatus = 0;
                                if (GP.mood <= 0) {
                                    GP.death(1);
                                } else if (GP.army <= 0) {
                                    GP.death(2);
                                } else if (GP.cash <= 0) {
                                    GP.death(3);
                                } else if (GP.relig <= 0) {
                                    GP.death(4);
                                }
                            }
                        } else if ((x >= 560 && x <= 1030) && (y >= 1730 && y <= 1850)) {
                            //GP.drawCanvas();
                            GP.setMood(2);
                            GP.setArmy(2);
                            GP.setCash(2);
                            GP.setRelig(2);
                            //GP.drawStats();
                            if ((GP.mood > 0) && (GP.cash > 0) && (GP.army > 0) && (GP.relig > 0)) {
                                GP.year++;
                                GP.updateCard(0);
                            } else {
                                gameStatus = 0;
                                if (GP.mood <= 0) {
                                    GP.death(1);
                                } else if (GP.army <= 0) {
                                    GP.death(2);
                                } else if (GP.cash <= 0) {
                                    GP.death(3);
                                } else if (GP.relig <= 0) {
                                    GP.death(4);
                                }
                            }
                        }
                        touchPoints = 0;
                    }else if (gameStatus == 0){
                        if ((x >= 50 && x <= 520) && (y >= 1730 && y <= 1850)) {
                            GP.Update();
                            gameStatus = 1;
                        } else if ((x >= 560 && x <= 1030) && (y >= 1730 && y <= 1850)) {
                            finish();
                        }
                    }
                    break;
            }
            invalidate();
            return true;
        }
    }
    public void Data(){
        DataBase = Room.databaseBuilder(this, CardDataBase.class, "DataBaseForCards").build();
    }
    @Override
    protected void onResume() {
        super.onResume();
      }
}