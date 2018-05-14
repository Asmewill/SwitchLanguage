package com.ce.switchlanguage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Locale;

public class LocaleUtils {
    /**
     * 中文
     */
    public static final Locale LOCALE_CHINESE = Locale.CHINESE;
    /**
     * 英文
     */
    public static final Locale LOCALE_ENGLISH = Locale.ENGLISH;
    /**
     * 俄文
     */
    public static final Locale LOCALE_RUSSIAN = new Locale("ru");
    /**
     * bb文
     */
    public static final Locale LOCALE_BB = new Locale("bb");
    /**
     * 保存SharedPreferences的文件名
     */
    private static final String LOCALE_FILE = "LOCALE_FILE";
    /**
     * 保存Locale的key
     */
    private static final String LOCALE_KEY = "LOCALE_KEY";

    /**
     * 获取用户设置的Locale
     * @param pContext Context
     * @return Locale
     */
    public static Locale getUserLocale(Context pContext) {
        Locale locale=getObject(pContext,LOCALE_KEY,Locale.class);
       if(locale==null){
           locale=Locale.CHINESE;
       }else{
           locale=getObject(pContext,LOCALE_KEY,Locale.class);
       }
        return locale;
    }
    /**
     * 获取当前的Locale
     * @param pContext Context
     * @return Locale
     */
    public static Locale getCurrentLocale(Context pContext) {
        Locale _Locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0有多语言设置获取顶部的语言
            _Locale = pContext.getResources().getConfiguration().getLocales().get(0);
        } else {
            _Locale = pContext.getResources().getConfiguration().locale;
        }
        return _Locale;
    }
    /**
     * 更新Locale
     * @param pContext Context
     * @param pNewUserLocale New User Locale
     */
    public static void updateLocale(Context pContext, Locale pNewUserLocale) {
            Configuration _Configuration = pContext.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                _Configuration.setLocale(pNewUserLocale);
            } else {
                _Configuration.locale =pNewUserLocale;
            }
            DisplayMetrics _DisplayMetrics = pContext.getResources().getDisplayMetrics();
            pContext.getResources().updateConfiguration(_Configuration, _DisplayMetrics);
            saveObject(pContext,LOCALE_KEY,pNewUserLocale);
           // saveUserLocale(pContext, pNewUserLocale);

    }
    /**
     * 判断需不需要更新
     * @param pContext Context
     * @param pNewUserLocale New User Locale
     * @return true / false
     */
    public static boolean needUpdateLocale(Context pContext, Locale pNewUserLocale) {
        return pNewUserLocale != null && !getCurrentLocale(pContext).equals(pNewUserLocale);
    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
    public static void saveObject(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(LOCALE_FILE, 0);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static  <T> T getObject(Context context, String key, Class<T> clazz) {
        SharedPreferences sp = context.getSharedPreferences(LOCALE_FILE, 0);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
