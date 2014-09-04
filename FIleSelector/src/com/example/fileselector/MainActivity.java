package com.example.fileselector;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	File rootDir;
	File currFolder;
	File[] arFiles;
	TextView textView1;
	LayoutInflater inflater;
	private LinearLayout llFileholder;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		initUI();
		createViewDynamically();
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
				
					findViewById(R.id.ibParent).setEnabled(false);
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
				
				((ImageView)v.findViewById(R.id.icon)).setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v) 
					{
						int pos=Integer.parseInt(v.getTag().toString());
						
						File selectedFile=arFiles[pos];
						
						Toast.makeText(getApplicationContext(), selectedFile.getName(), Toast.LENGTH_LONG).show();
					}
				});
			}
			else
			{
				((ImageView)v.findViewById(R.id.icon)).setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v) 
					{
						int pos=Integer.parseInt(v.getTag().toString());
						
						currFolder=arFiles[pos];
						
						createViewDynamically();
					}
				});
				
			}
			llFileholder.addView(v);
				
		}

	}

}
