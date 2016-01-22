package com.evh98.cd;


public class Setup extends ListActivity {
	
	static final String[] options = {"Event Name", "Event Date", "Event Time", "Event Icon", "Event Theme", "Finish"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Setup.this, R.layout.layout_setup1, options));
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		switch (position) {
        case 0:
        	DialogFragment f1 = new TextEditFragment();
        	f1.show(getFragmentManager(), "textedit");
        	break;
        case 1:
        	DialogFragment f2 = new DatePickerFragment();
        	f2.show(getFragmentManager(), "datepicker");
        	break;
        case 2:
        	DialogFragment f3 = new TimePickerFragment();
        	f3.show(getFragmentManager(), "timepicker");
        	break;
        case 3:
        	DialogFragment f4 = new IconPickerFragment();
        	f4.show(getFragmentManager(), "iconpicker");
        	break;
        case 4:
        	DialogFragment f5 = new ThemePickerFragment();
        	f5.show(getFragmentManager(), "themepicker");
        	break;
        case 5:
        	Main.writeEvent(Main.newEvent, new File(getExternalFilesDir(null), Main.newEvent.getName() + ".event"));
			Toast.makeText(getApplicationContext(), "Event created", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(Setup.this, Main.class));
			Main.update();
			
			Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName());
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			
        	break;
		}
	}
}