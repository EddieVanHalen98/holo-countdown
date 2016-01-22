package com.evh98.cd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

public class TextEditFragment extends DialogFragment{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
    	alert.setTitle("Set name");
    	final EditText input = new EditText(getActivity());
    	alert.setView(input);
    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Editable e = input.getText();
				Main.newEvent.setName(e.toString());
				Toast.makeText(getActivity(), "Event name set", Toast.LENGTH_SHORT).show();
			}
		});
    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
    	return alert.create();
	}
	
}