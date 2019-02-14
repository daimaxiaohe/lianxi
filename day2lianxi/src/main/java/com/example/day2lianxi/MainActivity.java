package com.example.day2lianxi;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.day2lianxi.annotion.BindOnClick;
import com.example.day2lianxi.annotion.DraweeViewAnnotation;
import com.example.day2lianxi.annotion.ZBindView;
import com.example.day2lianxi.entity.Buffnife;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @DraweeViewAnnotation("Hello world")
    @ZBindView(R.id.simple)
    SimpleDraweeView simple;
    @ZBindView(R.id.circle)
    Button circle;
    @ZBindView(R.id.round)
    Button round;
    @ZBindView(R.id.bili)
    Button bili;
    @ZBindView(R.id.loadgif)
    Button loadgif;
    private GenericDraweeHierarchy hierarchy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Buffnife.bind(this);
        initView();
        initData();
    }
    /**
     * 操作视图的方法
     */
    private void initView() {
        setload();
    }
    public void setload(){
        Uri uri = Uri.parse("http://www.zhaoapi.cn/images/quarter/ad1.png");
        simple.setImageURI(uri);
    }
    /**
     * 操作数据的方法
     */
    private void initData() {
        //更改图片的效果
        hierarchy = simple.getHierarchy();
        setload();
    }

    @BindOnClick(R.id.circle)
    public void setCircle(){
        hierarchy.setRoundingParams(RoundingParams.asCircle());
        setload();
    }
    @BindOnClick(R.id.round)
    public void setround(){
        hierarchy.setRoundingParams(RoundingParams.fromCornersRadius(20));
        setload();
    }
    @BindOnClick(R.id.bili)
    public void setbili(){
        simple.setAspectRatio(2.71f);
        setload();
    }
    @BindOnClick(R.id.loadgif)
    public void setloadgif(){
        Uri uri = Uri.parse("http://www.zhaoapi.cn/images/girl.gif");
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        simple.setController(draweeController);
    }
    @BindOnClick(R.id.gain)
    public void setgain(){
        Class<MainActivity> mainActivityClass = MainActivity.class;
        try {
            Field simple = mainActivityClass.getDeclaredField("simple");
            DraweeViewAnnotation annotation = simple.getAnnotation(DraweeViewAnnotation.class);
            Toast.makeText(MainActivity.this,annotation.value()+"",Toast.LENGTH_SHORT).show();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    @BindOnClick(R.id.add)
    public void setadd(){

    }
}
