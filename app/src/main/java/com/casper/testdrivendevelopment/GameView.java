package com.casper.testdrivendevelopment;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder;
    DrawThread drawThread;
    ArrayList<Sprite> sprites=new ArrayList<Sprite>();

    float xTouch=-1,yTouch=-1;
    int count = 0;
    public GameView(Context context) {
        super(context);
        holder=this.getHolder();

        holder.addCallback(this);

        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());


        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                xTouch=event.getX();
                yTouch=event.getY();
                return false;
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread=new DrawThread();
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(null!=drawThread)
        {
            drawThread.stopThread();
            drawThread=null;
        }
    }

    private class DrawThread extends Thread {
        private Boolean beAlive=true;
        public void stopThread() {
            beAlive=false;
        }
        @Override
        public void run() {
            while(beAlive) {
                xTouch=-1;
                yTouch=-1;

                try{
                    //获得绘画的画布
                    Canvas canvas =  holder.lockCanvas();
                    //背景设成蓝色
                    canvas.drawColor(Color.WHITE);

                    //在20，40的地方画一个文本hello world!
                    Paint p = new Paint();
                    p.setTextSize(50);
                    p.setColor(Color.BLACK);
                    canvas.drawText("击中了"+count +"个",20,60,p);
                    if(xTouch>0) {
                        for(Sprite sprite:sprites){
                            if(xTouch<sprite.getX()+30 && xTouch>sprite.getX()-30){
                                if(yTouch<sprite.getY()+30&&yTouch>sprite.getY()-30){
                                    ++count;
                                    sprite.reset();

                                }
                            }
                        }

                    }
                    else

                    for (Sprite sprite:sprites) {
                        sprite.move();
                    }

                    for (Sprite sprite:sprites) {
                        canvas.drawCircle(sprite.getX(),sprite.getY(),30,p);
                    }

                    holder.unlockCanvasAndPost(canvas);//解锁
                    Thread.sleep(10);
                }catch (Exception e){
                }
            }
        }
    }

    private class Sprite {
        int x,y;
        double directionAgle;

        public Sprite() {
            x= (int) (GameView.this.getWidth()*Math.random());
            y= (int) (GameView.this.getHeight()*Math.random());
            directionAgle=Math.random()*2*Math.PI;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move()
        {
            x+=15*Math.cos(directionAgle);
            y+=15*Math.sin(directionAgle);
            if(x<0) x+=GameView.this.getWidth();
            if(x>GameView.this.getWidth())x-=GameView.this.getWidth();
            if(y<0) y+=GameView.this.getHeight();
            if(y>GameView.this.getHeight())y-=GameView.this.getHeight();

            if(Math.random()<0.05)directionAgle=Math.random()*2*Math.PI;
        }
        public void reset(){
            x=(int)Math.random()%GameView.this.getWidth();
            y = (int)Math.random()%GameView.this.getHeight();
        }

    }
}
