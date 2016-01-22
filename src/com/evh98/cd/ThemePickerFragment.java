package com.evh98.cd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class ThemePickerFragment extends DialogFragment{

	public String[] colors = {"Blue", "Dark Blue", "Purple", "Dark Purple", "Green", "Dark Green", "Orange", "Dark Orange", "Red", "Dark Red", "Grey", "Black"};
	public String[] colorCodes = {"#33B5E5", "#0099CC", "#AA66CC", "#9933CC", "#99CC00", "#669900", "#FFBB33", "#FF8800", "#FF4444", "#CC0000", "#4C4C4C", "#303030"};
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
    	alert.setTitle("Set icon");
    	alert.setItems(colors, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Main.newEvent.setTheme(colorCodes[which]);
				Toast.makeText(getActivity(), "Event theme set", Toast.LENGTH_SHORT).show();
			}
		});
    	return alert.create();
	}
}