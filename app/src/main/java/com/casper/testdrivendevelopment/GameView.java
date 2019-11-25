package com.casper.testdrivendevelopment;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder;
    DrawThread drawThread;
    ArrayList<Sprite> sprites=new ArrayList<Sprite>();


    float xTouch=200,yTouch=300;
    int count = 0;

    boolean re = false;

    public GameView(Context context) {
        super(context);
        holder=this.getHolder();

        holder.addCallback(this);

        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());





    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //实时获取手指停留处的xy坐标
        xTouch = (float) event.getX();
        yTouch = (float) event.getY();
        return true;
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

                try{
                    //获得绘画的画布
                    Canvas canvas =  holder.lockCanvas();
                    //背景设成白色
                    canvas.drawColor(Color.WHITE);


                    //在20，40的地方画一个文本hello world!
                    Paint p = new Paint();
                    p.setTextSize(50);

                    Paint user = new Paint();
                    user.setColor(Color.GREEN);

                    p.setColor(Color.BLACK);
                    canvas.drawText("碰撞了"+count +"次",20,60,p);


                    canvas.drawCircle(xTouch,yTouch,30,user);
                    if(xTouch>0) {
                        for(Sprite sprite:sprites) {
                            if(isCollsionWithCircle(xTouch,yTouch,30,sprite.getX(),sprite.getY(),30)){
                                user.setColor(Color.RED);
                                canvas.drawCircle(xTouch,yTouch,30,user);
                                ++count;

                            }

                        }

                    }


                    for (Sprite sprite:sprites) {
                        sprite.move();

                    }

                    p.setColor(Color.BLACK);
                    for (Sprite sprite:sprites) {
                        canvas.drawCircle(sprite.getX(),sprite.getY(),30,p);
                    }



                    holder.unlockCanvasAndPost(canvas);//解锁
                    Thread.sleep(15);
                }catch (Exception e){
                }
            }
        }

    }


    public boolean isCollsionWithCircle(float x1,float y1,int r1,float x2,float y2,int r2){
        float SquareX = (x1-x2)*(x1-x2);
        float SquareY = (y1-y2)*(y1-y2);

        if(SquareX < r1*r1 ||SquareX < r2*r2){
            if(SquareY < r1*r1 || SquareY < r2*r2){
                return true;
            }
        }
        return false;
    }

    public class Sprite {
        float x,y;
        double directionAngle;

        public Sprite() {
            x= (float) (GameView.this.getWidth()*Math.random());
            y= (float) (GameView.this.getHeight()*Math.random());
            directionAngle =Math.random()*2*Math.PI;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public void move()
        {
            x+=20*Math.cos(directionAngle);
            y+=20*Math.sin(directionAngle);
            if(x<0) x+=GameView.this.getWidth();
            if(x>GameView.this.getWidth())x-=GameView.this.getWidth();
            if(y<0) y+=GameView.this.getHeight();
            if(y>GameView.this.getHeight())y-=GameView.this.getHeight();

            if(Math.random()<0.05) directionAngle =Math.random()*2*Math.PI;
        }


    }
}
