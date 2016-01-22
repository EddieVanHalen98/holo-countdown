package com.evh98.cd;

import android.app.ListActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

public class WidgetConfig extends ListActivity{

	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	String[] events;
	String event;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setResult(RESULT_CANCELED);
		
		events = Main.eventT.toArray(new String[Main.eventT.size()]);
		
		setListAdapter(new ArrayAdapter<String>(WidgetConfig.this, R.layout.layout_setup1, events));
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));
		
		Intent intent = getIntent();
	    Bundle extras = intent.getExtras();
	    if (extras != null) {
	    	mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	    }
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
	         finish();
	    }
		
		final Context c = WidgetConfig.this;
		AppWidgetManager awm = AppWidgetManager.getInstance(c);
		RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.layout_widget);
		awm.updateAppWidget(mAppWidgetId, views);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		WidgetProvider.e = Main.events.get(position);
		
		Intent rV = new Intent();
		rV.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, rV);
		finish();
		
		final Context c = WidgetConfig.this;
		AppWidgetManager awm = AppWidgetManager.getInstance(c);
		RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.layout_widget);
		awm.updateAppWidget(mAppWidgetId, views);
	}
}