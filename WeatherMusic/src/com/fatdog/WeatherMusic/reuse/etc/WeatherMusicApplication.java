package com.fatdog.WeatherMusic.reuse.etc;

import java.lang.reflect.Field;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;

public class WeatherMusicApplication extends Application {
	private MediaPlayer mp; // 공통적인 미디어 플레이어객체 사용
	
	private String userId;
	private String userName;
	
	public WeatherMusicApplication() {
		super();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		WeatherMusicApplication.setDefaultFont(this, "DEFAULT", "sandol_light.otf");
		mp = new MediaPlayer( );
	}

	public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }
	
	// 글자를 애플리케이션 전체에 적용하기 위해 사용한다.
    protected static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

	public MediaPlayer getMediaPlayer( ) { // 다른 액티비티나 프래그먼트에서 사용할 수 있다.
		return mp;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}