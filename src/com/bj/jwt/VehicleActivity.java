package com.bj.jwt;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.TabActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.OnTabChangeListener;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
public class VehicleActivity extends Activity{
	public EditText etname,etphone,etarchivesnum,etidentitycard,etdrivetype,etlicence;
	public Spinner sppeopletype,spprovince,spfzjg;
	public Spinner spcartype,spcarnumtype,sptraffictype,spprovincecar;
	public EditText etcarnum,etroadcode,etroadname;
	public EditText etlawnum,etlawdisinfo;
	public String slawnum,slawmoney,slawscore,slawarticles,slawinfo,slawqzcslx; 
	public Button btn_title;
	private static final String XZQH="110105";
	private static String PUNISHTYPE;  //处罚类型
	private CSimplePunish csp = new CSimplePunish();
	private TabHost mTabHost=null;
	myDBAdapter mydb;
	public MainActivity  mActivity =null;
	public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState); 
        Intent intent =getIntent();
    	String punishtype =intent.getStringExtra("punishtype");
        InsertTab(punishtype);
        PUNISHTYPE=punishtype;
        mydb =new myDBAdapter(VehicleActivity.this);
        btn_title=(Button)findViewById(R.id.tab_title);
       if(!mydb.openImportDB())return;
    }
	/*
public void InsertTab1()
{
	TabHost mTabHost = getTabHost();
    LayoutInflater inflater_tab1 = LayoutInflater.from(this);   
    inflater_tab1.inflate(R.layout.activity_simplebaseinfo, mTabHost.getTabContentView());  
    inflater_tab1.inflate(R.layout.activity_simplecarinfo, mTabHost.getTabContentView());  
    inflater_tab1.inflate(R.layout.activity_simplelawinfo, mTabHost.getTabContentView());  
    inflater_tab1.inflate(R.layout.activity_vehiclepunish, mTabHost.getTabContentView()); 
    mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB 11").setContent(R.id.Vehiclebaseinfolayout));  
    mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB 12").setContent(R.id.Vehiclecarinfolayout)); 
    mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB 13").setContent(R.id.Vehiclelawinfolayout));  
    mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB 14").setContent(R.id.Vehiclepunishlayout));    
}
*/
	
public void InsertTab(String type) {   
    setContentView(R.layout.activity_punishtab);   
    mTabHost = (TabHost)findViewById(R.id.tabhost); 
    mTabHost.setup();
    LayoutInflater inflater_tab1 = LayoutInflater.from(this);   
    inflater_tab1.inflate(R.layout.activity_simplebaseinfo, mTabHost.getTabContentView());  
    inflater_tab1.inflate(R.layout.activity_simplecarinfo, mTabHost.getTabContentView());  
    inflater_tab1.inflate(R.layout.activity_simplelawinfo, mTabHost.getTabContentView());  
    inflater_tab1.inflate(R.layout.activity_printview, mTabHost.getTabContentView());  
    mTabHost.addTab(mTabHost.newTabSpec("baseinfo").setIndicator("基本信息").setContent(R.id.Vehiclebaseinfolayout));  
    mTabHost.addTab(mTabHost.newTabSpec("carinfo").setIndicator("车辆信息").setContent(R.id.Vehiclecarinfolayout));
    mTabHost.addTab(mTabHost.newTabSpec("lawinfo").setIndicator("违法信息").setContent(R.id.Vehiclelawinfolayout));  
    mTabHost.addTab(mTabHost.newTabSpec("finish").setIndicator("处理").setContent(R.id.printviewlayout)); 
    updateTab(mTabHost);
    mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
		
		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			updateTab(mTabHost);
			if(tabId.equals("carinfo"))
			{
				IsBaseinfofull();
			}
			if(tabId.equals("lawinfo"))
			{
				IsCarinfofull();
			}
			if(tabId.endsWith("finish"))
			{
				if(!IsBaseinfofull()||!IsCarinfofull()||!IsLawinfofull())
				{
					return;
				}
				else
				{ 
					String sprint =csp.printpunish();
					EditText etprintview =(EditText)findViewById(R.id.etprintview);
					etprintview.setText(sprint);
				}
			}
		}
	});
    GetAllViewId(); 
    if(type.equals("s"))   //简易处罚
    {
    	LinearLayout rlayout =(LinearLayout)findViewById(R.id.Vehiclelawinfolayoutforce);
	    rlayout.setVisibility(View.INVISIBLE);
	    
	    //RelativeLayout splayout =(RelativeLayout)findViewById(R.id.punishtabhost);
	  //  splayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_svehiclepunish));
	   // btn_title.setText(R.string.tvspunish);
    }  
    if(type.equals("f"))  //强制措施
    {
    	LinearLayout rlayout =(LinearLayout)findViewById(R.id.Vehiclelawinfolayoutforce);
	    rlayout.setVisibility(View.VISIBLE);
	   // RelativeLayout splayout =(RelativeLayout)findViewById(R.id.punishtabhost);
	   // splayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fvehiclepunish));
	    //btn_title.setText(R.string.tvfpunish);
    }
    if(type.equals("n"))  //通知书
    {
    	LinearLayout rlayout =(LinearLayout)findViewById(R.id.Vehiclelawinfolayoutforce);
	    rlayout.setVisibility(View.VISIBLE);
	   // RelativeLayout splayout =(RelativeLayout)findViewById(R.id.punishtabhost);
	  //  splayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fvehiclenotification));
	   // btn_title.setText(R.string.tvfpunish);
    }
}
/**
 * 更新Tab标签的颜色，和字体的颜色
 * @param tabHost
 */ 
private void updateTab(final TabHost tabHost) { 
    for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) { 
        View view = tabHost.getTabWidget().getChildAt(i); 
        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); 
        tv.setTextSize(20); 
        tv.setTypeface(Typeface.SERIF, 2); // 设置字体和风格  
        if (tabHost.getCurrentTab() == i) {//选中  
            //view.setBackgroundDrawable(getResources().getDrawable(R.drawable.category_current));//选中后的背景  
            tv.setTextColor(this.getResources().getColorStateList( 
                    android.R.color.black)); 
        } else {//不选中  
            //view.setBackgroundDrawable(getResources().getDrawable(R.drawable.category_bg));//非选择的背景  
            tv.setTextColor(this.getResources().getColorStateList( 
                    android.R.color.darker_gray)); 
        } 
    } 
} 

/*
 * 获得简易处罚所有view的id
 */
public void GetAllViewId()
{
	//获得驾驶员信息id
	
		etname=(EditText)findViewById(R.id.etname);
		etphone=(EditText)findViewById(R.id.etphone);	
		etarchivesnum=(EditText)findViewById(R.id.etarchivesnum);	
		etidentitycard=(EditText)findViewById(R.id.etidentitycard);	
		etdrivetype=(EditText)findViewById(R.id.etdrivetype);	
		sppeopletype =(Spinner)findViewById(R.id.sppeopletype);
		etlicence =(EditText)findViewById(R.id.etlicence);
		spprovince=(Spinner)findViewById(R.id.spprovince);
		spprovince.setOnItemSelectedListener( new ProvinceOnItemSelectedListener());
		spfzjg=(Spinner)findViewById(R.id.spprovince1);
		
		spfzjg.setOnItemSelectedListener(new fzjgOnItemSelectedListener());
		
		ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.peopletypearray, R.layout.simple_myspinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sppeopletype.setAdapter(adapter2);
		
		adapter2 = ArrayAdapter.createFromResource(this, R.array.provincearray, R.layout.simple_myspinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spprovince.setAdapter(adapter2);
		
		//获得车辆信息id
		spcartype=(Spinner)findViewById(R.id.spcartype);
		adapter2 = ArrayAdapter.createFromResource(this, R.array.cartypearray, R.layout.simple_myspinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spcartype.setAdapter(adapter2);
		spcarnumtype=(Spinner)findViewById(R.id.spcarnumtype);
		adapter2 = ArrayAdapter.createFromResource(this, R.array.carnumtypearray, R.layout.simple_myspinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spcarnumtype.setAdapter(adapter2);
		
		sptraffictype=(Spinner)findViewById(R.id.sptraffictype);
		adapter2 = ArrayAdapter.createFromResource(this, R.array.traffictypearray, R.layout.simple_myspinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sptraffictype.setAdapter(adapter2);
		spprovincecar=(Spinner)findViewById(R.id.spprovince_car);
		adapter2 = ArrayAdapter.createFromResource(this, R.array.provincearray, R.layout.simple_myspinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spprovincecar.setAdapter(adapter2);
		
		etcarnum=(EditText)findViewById(R.id.etcarnum);
		etroadcode=(EditText)findViewById(R.id.etroadcode);
		etroadname=(EditText)findViewById(R.id.etroadname);
		etroadcode.addTextChangedListener(new RoadCodeTextWatcher());
		//获得违法信息id
		etlawnum=(EditText)findViewById(R.id.etlawcode);
		etlawnum.addTextChangedListener(new LawNumTextWatcher());
		etlawdisinfo =(EditText)findViewById(R.id.etlawinfo);
		//etlawdisinfo.setMovementMethod(ScrollingMovementMethod.getInstance());
	}
//基本信息监听部分
 public class ProvinceOnItemSelectedListener implements OnItemSelectedListener
 {

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
	/*	Toast.makeText(VehicleActivity.this,"选择的省份： " +  

	             arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();  
		*/

		String sqlcmd;//String.format("select mc2 from code_fzjg where dm like '%s%'", arg0.getItemAtPosition(arg2).toString());
		sqlcmd="select mc2 from code_fzjg where dm like "+"'"+arg0.getItemAtPosition(arg2).toString()+"%'";
		Cursor cursor ;
		cursor=mydb.queryentry(sqlcmd);
		ArrayAdapter<String> adpter =new ArrayAdapter<String>(VehicleActivity.this, R.layout.simple_myspinner_item); 
		
		while(cursor.moveToNext())
		{
			String result =cursor.getString(0);
			System.out.println("------"+result);
			adpter.add(result);
		}
		adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spfzjg.setAdapter(adpter);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	 
 }
 public class fzjgOnItemSelectedListener implements OnItemSelectedListener
 {

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
		String sqlcmd;//String.format("select mc2 from code_fzjg where dm like '%s%'", arg0.getItemAtPosition(arg2).toString());
		sqlcmd="select dm from code_fzjg where mc2="+"'"+arg0.getItemAtPosition(arg2).toString()+"'";
		Cursor cursor ;
		cursor=mydb.queryentry(sqlcmd);
		while(cursor.moveToNext())
		{
			String result =cursor.getString(0);
			System.out.println("------"+result);
			etlicence.setText(result);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	 
 }
 //车辆信息的监听程序

 public class RoadCodeTextWatcher implements TextWatcher
 {

	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		String strroadcode=etroadcode.getText().toString().trim();
		if(strroadcode.length()==5)
		{
			String strsqlcmd="select "+XZQH+",dm,mc from code_wfdd where dm="+"'"+strroadcode+"'"+" and xzqh like '%"+XZQH+"%'";
			Cursor cursor;
			cursor=mydb.queryentry(strsqlcmd);
			while (cursor.moveToNext())
			{
				String result =cursor.getString(2);
				System.out.println("------"+result);
				etroadname.setText(result);
			}
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
 }
 //违法代码监控信息
 public class LawNumTextWatcher implements TextWatcher
 {
	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		String strlawnum=etlawnum.getText().toString().trim();
		if((strlawnum.length()==5)||(strlawnum.length()==4))
		{
			String strsqlcmd="select * from code_jy_wfdm";
			Cursor cursor;
			if(PUNISHTYPE.endsWith("s"))
			{
				strsqlcmd="select WFDM,WFXW,CFYJ,FKJE,WFJFS,0 as QZCSLX from code_jy_wfdm where wfdm="+"'"+strlawnum+"'";//'10052'";
				
			}
			if(PUNISHTYPE.endsWith("f"))
			{
				strsqlcmd="select WFDM,WFNR as WFXW,CFYJ,FKJE_DUT as FKJE, WFJFS, QZCSLX from code_qzcs_wfdm where wfdm="+"'"+strlawnum+"'";//'10052'";
			}
			cursor=mydb.queryentry(strsqlcmd);
			if(cursor.getCount()==1)
			{
				while (cursor.moveToNext())
				{
					slawnum =cursor.getString(cursor.getColumnIndex("WFDM"));
					slawinfo =cursor.getString(cursor.getColumnIndex("WFXW"));
					slawarticles =cursor.getString(cursor.getColumnIndex("CFYJ"));
					slawmoney =cursor.getString(cursor.getColumnIndex("FKJE"));
					slawscore =cursor.getString(cursor.getColumnIndex("WFJFS"));
					slawqzcslx=cursor.getString(cursor.getColumnIndex("QZCSLX"));
				}
				etlawdisinfo.setText("违法行为："+slawinfo+"\n"+
						"违法依据:" +slawarticles+"\n"+
						"违章积分:"+slawscore+"\n"+
						"罚款金额:"+slawmoney+"\n");
			}
			
		}
		else
		{
			etlawdisinfo.setText(null);
		}
	}
	 
 }
/*
 * 检测基本信息是否填写完成
 */
public boolean IsBaseinfofull()
{	
	csp.steam="海淀区交警支队";
	csp.sgoverment="海淀区人民政府";
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	csp.spunishdate=df.format(new Date());
			
	csp.sname=etname.getText().toString().trim();	
	csp.sphone =etphone.getText().toString().trim();	
	csp.sarchivesnum=etarchivesnum.getText().toString().trim();	
	csp.sidentitycard=etidentitycard.getText().toString().trim();
	csp.sdrivetype=etdrivetype.getText().toString().trim();
	csp.speopletype=sppeopletype.getSelectedItem().toString().trim();
	csp.speopletype= csp.speopletype.substring(0, 1);
	csp.slicence=etlicence.getText().toString().trim();
	csp.spolicename="唐sir";
	csp.spolicenum="110110";
	if((csp.sname.length()<2)||(csp.sphone.length()<7)||(csp.sarchivesnum.length()<10)||(csp.sidentitycard.length()<17)
	||(csp.sdrivetype.length()<=0)||(csp.speopletype.length()!=1))
	{
		
		Toast toast =Toast.makeText(getApplicationContext(), "信息录入不正确", Toast.LENGTH_SHORT);
		
		toast.setGravity(Gravity.CENTER, toast.getXOffset()/2, toast.getYOffset()/2);
		toast.show();
		return false;
	}
	/*
	if(csp.sphone.length()<7)
	{
		return false;
	}
	if(csp.sarchivesnum.length()<10)
	{
		return false;
	}
	if(csp.sidentitycard.length()<17)
	{
		return false;
	}
	if(csp.sdrivetype.length()<=0)
	{
		return false;
	}
	if(csp.speopletype.length()!=1)
	{
		
		return false;
	}*/
	return true;
}
public boolean IsCarinfofull()
{
	 csp.scartype=spcartype.getSelectedItem().toString().trim().substring(0,1);
	 csp.scarnumtype=spcarnumtype.getSelectedItem().toString().trim().substring(0,1);
	 csp.scarnum=spprovincecar.getSelectedItem().toString().trim()+etcarnum.getText().toString().trim();
	 csp.straffictype =sptraffictype.getSelectedItem().toString().trim().substring(0,1);
	 csp.sroadnum=etroadcode.getText().toString().trim();
	 csp.sroadname=etroadname.getText().toString().trim();
	 if((csp.scartype.length()!=1)||(csp.scarnumtype.length()!=1)||(csp.scarnum.length()<6)
			 ||(csp.straffictype.length()!=1)||(csp.sroadnum.length()!=5)||(csp.sroadname.length()<=2))
				{
					
					Toast toast =Toast.makeText(getApplicationContext(), "车辆信息录入不完整", Toast.LENGTH_SHORT);
					
					toast.setGravity(Gravity.CENTER, toast.getXOffset()/2, toast.getYOffset()/2);
					toast.show();
					return false;
				}
	return true;
	}
public boolean IsLawinfofull()
{
	csp.slawcode=etlawnum.getText().toString().trim();
	csp.slawinfo=slawinfo;
	csp.slawmoney=slawmoney;
	csp.slawscore=slawscore;
	csp.slawarticles=slawarticles;
	
	 if((csp.slawscore.length()<1)||(csp.slawinfo.length()<5)||(csp.slawmoney.length()<2)
			 ||(csp.slawcode.length()<4)||(csp.slawarticles.length()<5))
				{
					
					Toast toast =Toast.makeText(getApplicationContext(), "违法信息录入不完整", Toast.LENGTH_SHORT);	
					toast.setGravity(Gravity.CENTER, toast.getXOffset()/2, toast.getYOffset()/2);
					toast.show();
					return false;
				}
	return true;
	}
/*
 * 查询基本信息数据 ，测试
 */
public void OnClickbasequery(View v)
{
	etname.setText("张三");
	etphone.setText("13901011000");
	etidentitycard.setText("110102197804090110");
	etdrivetype.setText("C1");
	etarchivesnum.setText("110005003111");
}
//车辆信息
public void OnClickCarquery(View v)
{
	// etroadcode.setText("71781102");
	// etroadname.setText("定慧寺路口至西八里庄路口段");
}

//违法信息
public void OnClicklawcodequery(View v)
{
	
	}
/*
 * 打印数据
 */
public void OnClickprint(View v)
{
	if(IsBaseinfofull()&&IsCarinfofull()&&IsLawinfofull())
	{
		String sprint =csp.printpunish();
		EditText etprintview =(EditText)findViewById(R.id.etprintview);
		etprintview.setText(sprint);
		if(!MainActivity.sendprintinfo(sprint))
		{
		Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
		return;
		}
	}
}
public void OnClickpunishBack(View v)
{
	this.finish();
	}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	mydb.close();
	super.onDestroy();
}
}
