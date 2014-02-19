		package course.labs.todomanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import course.labs.todomanager.ToDoItem.Priority;
import course.labs.todomanager.ToDoItem.Status;

public class ToDoListAdapter extends BaseAdapter {

	// List of ToDoItems
	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";
	
	// 3 days in milliseconds - 3 * 24 * 60 * 60 * 1000
	private static final long THREE_DAYS = 259200000;

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}
	
	// Clears the list adapter of all items.
	
	public void clear(){

		mItems.clear();
		notifyDataSetChanged();
	
	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	//Create a View to display the ToDoItem 
	// at specified position in mItems

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		//TODO - Get the current ToDoItem
		final ToDoItem toDoItem = (ToDoItem) this.getItem(position);

		
		//TODO - Inflate the View for this ToDoItem
		// from todo_item.xml.
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		final RelativeLayout itemLayout = (RelativeLayout)inflater.inflate(R.layout.todo_item, parent, false);
		
		//TODO - Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined 
		// in the layout file 

		
		// change the visibility of the warning icon
		long today = new Date().getTime();
		long diffDay = toDoItem.getDate().getTime() - today;
		
		ImageView warning = (ImageView)itemLayout.findViewById(R.id.warningIcon);
		
		if (diffDay < THREE_DAYS){
			warning.setVisibility(View.VISIBLE);
		}else{
			warning.setVisibility(View.INVISIBLE);
		}
		
		
		//TODO - Display Title in TextView

		final TextView titleView = 	(TextView)itemLayout.findViewById(R.id.titleView);
		titleView.setText(toDoItem.getTitle());
		// TODO - Set up Status CheckBox
	
		final CheckBox statusView = (CheckBox)itemLayout.findViewById(R.id.statusCheckBox);
		statusView.setChecked(toDoItem.getStatus()==Status.DONE);	
		
		if (statusView.isChecked()) {
			itemLayout.setBackgroundColor(Color.DKGRAY);
		}else{
			itemLayout.setBackgroundColor(Color.GRAY);
		}
		
		statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				log("Entered onCheckedChanged()");
				
				// TODO - Set up and implement an OnCheckedChangeListener, which 
				// is called when the user toggles the status checkbox
				if (isChecked){
					toDoItem.setStatus(Status.DONE);
					statusView.setChecked(true);
					itemLayout.setBackgroundColor(Color.DKGRAY);
				}else{
					toDoItem.setStatus(Status.NOTDONE);
					statusView.setChecked(false);
					itemLayout.setBackgroundColor(Color.GRAY);
				}

			
			}
		});

		//TODO - Display Priority in a TextView

		final TextView priorityView = (TextView)itemLayout.findViewById(R.id.priorityView);
		
		priorityView.setText(toDoItem.getPriority().toString());
		
		/*ArrayAdapter<Priority> adapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, Priority.values());

		
		priorityView.setAdapter(new ArrayAdapter<Priority>(this,android.R.layout.simple_spinner_item, Priority.values());
		priorityView.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				toDoItem.setPriority(Priority.HIGH);
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		*/
		
		// TODO - Display Time and Date. 
		// Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and time String

		final TextView dateView = (TextView)itemLayout.findViewById(R.id.dateView);
		dateView.setText(toDoItem.FORMAT.format(toDoItem.getDate()));
		
		

		// Return the View you just created
		return itemLayout;

	}
	
	private void log(String msg) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.i(TAG, msg);
	}

}
