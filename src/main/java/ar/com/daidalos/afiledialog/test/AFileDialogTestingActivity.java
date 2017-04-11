
package ar.com.daidalos.afiledialog.test;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ar.com.daidalos.afiledialog.*;

public class AFileDialogTestingActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        // Assign behaviors to the buttons.
        Button buttonActivity1 = (Button)this.findViewById(R.id.activity_simple_open);
        buttonActivity1.setOnClickListener(btnActivitySimpleOpen);
    }
    
	private OnClickListener btnActivitySimpleOpen = new OnClickListener() {
    	public void onClick(View v) {
    		// Create the intent for call the activity.
            Intent intent = new Intent(AFileDialogTestingActivity.this, FileChooserActivity.class);
            
    		// Call the activity            
            AFileDialogTestingActivity.this.startActivityForResult(intent, 0);
    	}
	};
	
	// ---- Отображение результата ----- //
	
	private FileChooserDialog.OnFileSelectedListener onFileSelectedListener = new FileChooserDialog.OnFileSelectedListener() {
		public void onFileSelected(Dialog source, File file) {
			source.hide();
			Toast toast = Toast.makeText(AFileDialogTestingActivity.this, "File selected: " + file.getName(), Toast.LENGTH_LONG);
			toast.show();
		}
		public void onFileSelected(Dialog source, File folder, String name) {
			source.hide();
			Toast toast = Toast.makeText(AFileDialogTestingActivity.this, "File created: " + folder.getName() + "/" + name, Toast.LENGTH_LONG);
			toast.show();
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == Activity.RESULT_OK) {
	    	boolean fileCreated = false;
	    	String filePath = "";
	    	
	    	Bundle bundle = data.getExtras();
	        if(bundle != null)
	        {
	        	if(bundle.containsKey(FileChooserActivity.OUTPUT_NEW_FILE_NAME)) {
	        		fileCreated = true;
	        		File folder = (File) bundle.get(FileChooserActivity.OUTPUT_FILE_OBJECT);
	        		String name = bundle.getString(FileChooserActivity.OUTPUT_NEW_FILE_NAME);
	        		filePath = folder.getAbsolutePath() + "/" + name;
	        	} else {
	        		fileCreated = false;
	        		File file = (File) bundle.get(FileChooserActivity.OUTPUT_FILE_OBJECT);
	        		filePath = file.getAbsolutePath();
	        	}
	        }
	    	
	        String message = fileCreated? "File created" : "File opened";
	        message += ": " + filePath;
	    	Toast toast = Toast.makeText(AFileDialogTestingActivity.this, message, Toast.LENGTH_LONG);
			toast.show();
	    }
	}
}