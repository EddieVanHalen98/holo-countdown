package com.evh98.cd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class IconPickerFragment extends DialogFragment{

	public String[] icons = {"Calendar", "Cloud", "Labels", "Map", "Person", "Picture", "Phone"};
	public int[] iconCodes = {0x7f020004, 0x7f020002, 0x7f020005, 0x7f020006, 0x7f020008, 0x7f020009, 0x7f020001};
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
    	alert.setTitle("Set icon");
    	alert.setItems(icons, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Main.newEvent.setIcon(iconCodes[which]);
				Toast.makeText(getActivity(), "Event icon set", Toast.LENGTH_SHORT).show();
			}
		});
    	return alert.create();
	}
}