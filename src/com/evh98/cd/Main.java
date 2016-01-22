package com.evh98.cd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

	static Handler handler;
	static Runnable timedTask;
	
	public static ArrayList<Event> events = new ArrayList<Event>();
	public static ArrayList<String> eventT = new ArrayList<String>();
	public static Event newEvent = new Event();
	public static int currentEvent = 0;
	
	public static TextView text_count;
	public static TextView text_info;
	public static TextView text_fun1;
	public static TextView text_fun2;
	public static TextView text_fun3;
	public static TextView text_fun4;
	public static TextView text_fun5;
	public static TextView text_fun6;

	private static DrawerLayout mDrawerLayout;
	private static ListView mDrawerList;
	private static ActionBarDrawerToggle mDrawerToggle;
	
	static Calendar c = GregorianCalendar.getInstance();
	static long ms;
	static int m;
	static int h;
	static int d;
	static int mi;
	static int cr;
	static int ls;
	static int mf;
	static int me;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		
		handler = new Handler();
		timedTask = new Runnable(){
			@Override
			public void run(){
				update();
				handler.postDelayed(timedTask, 5000);
			}
		};
		
		readEvent();
		
		text_count = (TextView) findViewById(R.id.text_count);
		text_info = (TextView) findViewById(R.id.text_info);
		text_fun1 = (TextView) findViewById(R.id.text_fun1);
		text_fun2 = (TextView) findViewById(R.id.text_fun2);
		text_fun3 = (TextView) findViewById(R.id.text_fun3);
		text_fun4 = (TextView) findViewById(R.id.text_fun4);
		text_fun5 = (TextView) findViewById(R.id.text_fun5);
		text_fun6 = (TextView) findViewById(R.id.text_fun6);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));
		
		text_count.setText("Welcome");
		text_info.setText("to Holo Countdown");
		text_fun1.setText("Either create a new event");
		text_fun2.setText("or view an existing event");
		text_fun3.setText("");
		text_fun4.setText("Leave a rating in the Play Store!");
		text_fun5.setText("And make a donation!");
		text_fun6.setText("");
		getActionBar().setTitle("Countdown");
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, eventT));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
			public void onDrawerClosed(View v){}
			public void onDrawerOpened(View dv){}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
	}

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
	    switch (item.getItemId()) {
	        case R.id.menu_add:
			startActivity(new Intent(Main.this, Setup.class));
	            return true;
	        case R.id.menu_remove:
	        	new File(getExternalFilesDir(null), events.get(currentEvent).getName() + ".event").delete();
	        	events.remove(currentEvent);
	        	currentEvent = 1;
	        	update();
	        	Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
	        	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(i);
	    		getActionBar().setTitle("Countdown");
	    		getActionBar().setIcon(0x7f02000b);
				Toast.makeText(getApplicationContext(), "Event Removed", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.menu_donate:
	        	Intent i2 = new Intent(Intent.ACTION_VIEW);
	        	i2.setData(Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=437YMVJEBBN7A"));
	        	startActivity(Intent.createChooser(i2, "Open Webpage"));
	            return true;
	        case R.id.menu_contact:
	        	Intent i3 = new Intent(Intent.ACTION_SEND);
	        	i3.setData(Uri.parse("mailto:"));
	        	i3.setType("text/plain");
	        	i3.putExtra(Intent.EXTRA_EMAIL, new String[]{"ms@evh98.com"});
	        	i3.putExtra(Intent.EXTRA_SUBJECT, "Suggestion/Bug Report");
	        	startActivity(Intent.createChooser(i3, "Send Email"));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void selectItem(int position) {
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
		
		currentEvent = position;
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id){
        	selectItem(position);
        	
        	currentEvent = position;
    		getActionBar().setTitle(events.get(currentEvent).getName());
    		getActionBar().setIcon(events.get(currentEvent).getIcon());
    		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(events.get(currentEvent).getTheme())));
    		text_count.setTextColor(Color.parseColor(events.get(currentEvent).getTheme()));
    		text_info.setTextColor(Color.parseColor(events.get(currentEvent).getTheme()));
    		
    		for (int i = 0; i < parent.getCount(); i++) {
    		    TextView v = (TextView)parent.getChildAt(i);
    		    if (v != null)
    		        v.setTypeface(Typeface.DEFAULT);
    		}
    		
    		((TextView)view).setTypeface(Typeface.DEFAULT_BOLD);
        	
    		handler.post(timedTask);
        }
	}
	
	public static void calculateTime(){
		String[] date = events.get(currentEvent).getDate().split("#");
		String[] time = events.get(currentEvent).getTime().split("#");
		
		c.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
		
		ms = c.getTimeInMillis() - System.currentTimeMillis();
		
		m = (int)((ms/(1000*60))%60);
		h = (int)((ms/(1000*60*60))%24);
		d = (int)(ms/(1000*60*60*24));
		mi = (int)(ms/660000);
		cr = (int)(ms/324000);
		ls = (int)(ms/75);
		mf = (int)(ms/1210);
		me = (int)(ms/960000);
		
		text_count.setText(String.format("%02d:%02d:%02d",d,h,m));
		text_info.setText("Days : Hours : Minutes");
		text_fun1.setText("Approx. " + mi + " mile runs");
		text_fun2.setText("Approx. " + cr + " bathroom breaks");
		text_fun3.setText("Approx. " + ls + " lightsaber ignitions");
		text_fun4.setText("Approx. " + mf + " microfortnights");
		text_fun5.setText("Approx. " + (int)ms + " milliseconds");
		text_fun6.setText("Approx. " + me + " meals");
	}
	
	public static void writeEvent(Event ev, File f){
		try {
			FileOutputStream fout = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(ev);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readEvent(){
		
		File folder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/Android/data/com.evh98.cd/files/");
		
		for(final File f: folder.listFiles()){
			try {
				FileInputStream fin = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fin);
				Event ev = (Event) ois.readObject();
				ois.close();
				events.add(ev);
				eventT.add(ev.getName());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void update(){
		events.clear();
		eventT.clear();
		
		readEvent();
		
		if(!(events.size()>0)){
			text_count.setText("N/A");
			text_fun1.setText("");
			text_fun2.setText("");
			text_fun3.setText("");
			text_fun4.setText("");
			text_fun5.setText("");
		}else{
			calculateTime();
		}
		
		mDrawerLayout.closeDrawer(mDrawerList);
	}
}