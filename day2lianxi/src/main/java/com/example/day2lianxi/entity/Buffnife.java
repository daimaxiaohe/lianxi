package com.example.day2lianxi.entity;

import android.app.Activity;
import android.view.View;

import com.example.day2lianxi.annotion.BindOnClick;
import com.example.day2lianxi.annotion.ZBindView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Buffnife {

    public static void bind(Activity activity){
        initView(activity);
        initOnClik(activity);
    }

    /**
     * 获取控件的方法
     * @param activity
     */
    private static void initView(Activity activity) {
        //通过反射获取class
        Class<? extends Activity> aClass = activity.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            ZBindView annotation = field.getAnnotation(ZBindView.class);
            if (annotation!=null){
                int value = annotation.value();
                View view = activity.findViewById(value);
                try {
                    field.setAccessible(true);
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绑定点击事件的方法
     * @param activity
     */
    private static void initOnClik(final Activity activity) {
        //获取类的类类型
        Class<? extends Activity> aClass = activity.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (final Method method : methods) {
            BindOnClick annotation = method.getAnnotation(BindOnClick.class);
            if (annotation!=null){
                int[] valus = annotation.value();
                if (valus!=null&valus.length>0){
                    for (final int valu : valus) {
                        View viewById = activity.findViewById(valu);
                        if (viewById==null){
                            return;
                        }
                        viewById.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    method.setAccessible(true);
                                    method.invoke(activity,null);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        }
    }

}
