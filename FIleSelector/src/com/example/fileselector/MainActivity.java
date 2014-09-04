package com.example.fileselector;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	private File rootDir;
	private File currFolder;
	private File[] arFiles;
	private TextView textView1;
	private LayoutInflater inflater;
	private LinearLayout llFileholder;
	
private OnTouchListener onTouchListener=new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				v.setBackgroundColor(Color.parseColor("#ff83bdd5"));
				break;
			
			default:
				v.setBackgroundColor(Color.parseColor("#ff000000"));
				break;
			}event.getAction();
			return false;
		}
	};
protected int lastSelected;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initData();
		initUI();
		createViewDynamically();
		Toast.makeText(getApplicationContext(), "Press long on file to open it.", Toast.LENGTH_LONG).show();
	}

	

	private void initData()
	{
		inflater =(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

		rootDir = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()+ File.separator);//+ File.separator
		currFolder=rootDir;

		//arFiles=new Array[];
		arFiles=rootDir.listFiles();

		
	}

	private void initUI() 
	{
		//textView1=(TextView) findViewById(R.id.textView1);

		llFileholder=(LinearLayout)findViewById(R.id.llFileholder);

		findViewById(R.id.ibParent).setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				if(!currFolder.getAbsolutePath().equals(rootDir.getAbsolutePath()))
				{
					currFolder=currFolder.getParentFile();
					
					lastSelected=-1;
					//arFiles=currFolder.listFiles();
					createViewDynamically();
				}
				
			}
		});
		
		findViewById(R.id.ibHome).setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				
					currFolder=rootDir;
				
					lastSelected=-1;
					//findViewById(R.id.ibParent).setEnabled(false);
					//currFolder=currFolder.getParentFile();
					//arFiles=currFolder.listFiles();
					createViewDynamically();
				
				
			}
		});
		
		
	}


	private void createViewDynamically() 
	{
		if(!findViewById(R.id.ibParent).isEnabled()) findViewById(R.id.ibParent).setEnabled(true);
		
		arFiles=currFolder.listFiles();
		
		((TextView)findViewById(R.id.tvCurrFolder)).setText(currFolder.getName());
		
		llFileholder.removeAllViews();

		for(int i=0;i<arFiles.length;i++)
		{
			View v=inflater.inflate(R.layout.llsinglefolder, null);
			v.setTag(i);
			((ImageView)v.findViewById(R.id.icon)).setTag(i);
			((TextView)v.findViewById(R.id.tvFolder)).setText(arFiles[i].getName());
			
			if(arFiles[i].isFile())
			{
				((ImageView)v.findViewById(R.id.icon)).setBackgroundResource(R.drawable.file);
				
				v.setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v) 
					{
						int pos=Integer.parseInt(v.getTag().toString());
						
						File selectedFile=arFiles[pos];
						
						
						highliteSelectedFile(v,pos);
						
						//openSelectedFile(pos);
						Toast.makeText(getApplicationContext(), selectedFile.getName(), Toast.LENGTH_LONG).show();
					}
					

					
				});
				v.setOnLongClickListener(new OnLongClickListener()
				{
					
					@Override
					public boolean onLongClick(View v) 
					{
						int pos=Integer.parseInt(v.getTag().toString());
						
						highliteSelectedFile(v,pos);
						openSelectedFile(pos);
						return true;
					}
				});
			}
			else
			{
				v.setOnTouchListener(onTouchListener);
				v.setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v) 
					{
						int pos=Integer.parseInt(v.getTag().toString());
						
						lastSelected=-1;
						
						currFolder=arFiles[pos];
						
						createViewDynamically();
					}
				});
				
			}
			llFileholder.addView(v);
				
		}

	}
	
	protected void highliteSelectedFile(View v,int pos)
	{
		
		v.setBackgroundColor(Color.parseColor("#ff83bdd5"));
		
		
		
		if(lastSelected!=-1)
			{
			llFileholder.getChildAt(lastSelected).setBackgroundColor(Color.parseColor("#ff000000"));
			}
		lastSelected=pos;
		
	}



	private void openSelectedFile(int pos) 
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
	    Uri data = Uri.fromFile(arFiles[pos]);

	    intent.setDataAndType(data, "*/*");

	    startActivity(intent);
		
	}

}
