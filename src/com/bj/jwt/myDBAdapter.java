package com.bj.jwt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


public class myDBAdapter {
	//private static final String DATABASE_NAME="loginfo.db";
	private static final String DATABASE_TABLE="maintable";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_NAME="db_code.db";
	public  static final String PACKAGE_NAME="com.bj.jwt";
	public  static final String PATH_DB="/data"
	+Environment.getDataDirectory().getAbsolutePath()+"/"
	+PACKAGE_NAME;  //获取存储位置地址
	private final int BUFFER_SIZE =400000;
	
			
	
	//where 子句中使用的索引建列的名称
	public static final String KEY_ID="_id";
	//数据库中每个列的列名和索引
	public static final String KEY_NAME="name";
	public static final int NAME_COLUMN=1;
	//创建新数据库的sql语句
	private static final String DATABASE_CREATE ="create table "+
	DATABASE_TABLE+"("+KEY_ID+" integer primary key , "+
			KEY_NAME+" text not null);";
	//用来保存数据库实例的变量
	private SQLiteDatabase db;
	//使用数据库的应用程序上下文
	private Context context;
	//数据库的打开/升级helper
	private myDBHelper dbhelper;
	public myDBAdapter(Context _context)
	{
		context=_context;
		dbhelper= new myDBHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	public myDBAdapter(Context _context ,String dbname)
	{
		context=_context;
		dbhelper= new myDBHelper(context,dbname,null,DATABASE_VERSION);
	}
	//打开导入的db文件
	public  Boolean openImportDB()
	{
		db=this.opendatabase(PATH_DB+"/"+DATABASE_NAME);
		if (db==null) return false;
		else return true;
	
	}
	private SQLiteDatabase opendatabase(String dbfile)
	{
		
		try {
			
			if(!new File(dbfile).exists())
			{
				InputStream is =this.context.getResources().openRawResource(R.raw.db_code);//导入数据库
				FileOutputStream fos =new FileOutputStream(dbfile);
				byte[]buffer =new byte[BUFFER_SIZE];
				int count =0;
				while((count=is.read(buffer))>0)
				{
					fos.write(buffer,0,count);
				}
				is.close();
				fos.close();
			}
		SQLiteDatabase sdb =SQLiteDatabase.openOrCreateDatabase(dbfile, null);
		return sdb;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
		return null;
	}
	//打开新创建的数据库
	public myDBAdapter open()throws SQLException
	{
		try {
			db=dbhelper.getWritableDatabase();
		} catch (SQLiteException e) {
			// TODO: handle exception
			db=dbhelper.getReadableDatabase();
		}
		return this;
	}
	public void close()
	{
		if(db.isOpen())
		{
			db.close();
		}
		
	}
	//插入数据
	public long insertentry()
	{
		ContentValues newValues =new ContentValues();
		newValues.put(KEY_ID, 1);
		newValues.put(KEY_NAME, "黄");
		return db.insert(DATABASE_TABLE, null, newValues);
	}
	public Cursor queryentry(String sqlcmd)
	{
		//db.query(DATABASE_TABLE, new String[]{KEY_ID,KEY_NAME}, KEY_ID+"="+_rowIndex, null, null, null, orderBy)
		
		return db.rawQuery(sqlcmd, null);
	}
	private static class myDBHelper extends SQLiteOpenHelper
	{

		public myDBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
		//	db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			/*
			Log.w("TaskDBAdapter", "Upgrading from version "+
			oldVersion+" to "+
			newVersion+",which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			onCreate(db);
			*/
		}
		
	}
}
