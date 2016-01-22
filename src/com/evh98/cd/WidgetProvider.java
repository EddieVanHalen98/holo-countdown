package com.evh98.cd;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider extends AppWidgetProvider{

	public static Event e = Main.events.get(0);
	
	static Calendar ca = GregorianCalendar.getInstance();
	static long ms;
	static int m;
	static int h;
	static int d;
	
	@Override
	public void onUpdate(Context c, AppWidgetManager awm, int[] awis){
		super.onUpdate(c, awm, awis);
		
		final int N = awis.length;
		for(int i=0; i<N; i++){
			int awi = awis[i];
			updateAppWidget(c, awm, awi);
		}
	}
	
	public static void updateAppWidget(Context c, AppWidgetManager awm, int awi){
		String[] date = e.getDate().split("#");
		String[] time = e.getTime().split("#");
		Calendar ca = GregorianCalendar.getInstance();
		ca.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
		ms = ca.getTimeInMillis() - System.currentTimeMillis();
		m = (int)((ms/(1000*60))%60);
		h = (int)((ms/(1000*60*60))%24);
		d = (int)(ms/(1000*60*60*24));
		
		RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.layout_widget);
		views.setTextViewText(R.id.widget_text_count, String.format("%02d:%02d:%02d",d,h,m));
		views.setTextViewText(R.id.widget_text_info, e.getName());
		awm.updateAppWidget(awi, views);
		
		Toast.makeText(c, "Updated Holo Countdown" + String.format("%02d:%02d:%02d",d,h,m) + e.getName(), Toast.LENGTH_SHORT).show();
	}
	
}