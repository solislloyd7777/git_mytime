package com.example.mytime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyClass extends SQLiteOpenHelper {

    public static String dbName="MyDatabase";
    public static int dbVersion=1;
    public static String dbPath="";
    Context myContext;


    public MyClass(Context context) {
        super(context, dbName,null,dbVersion);
        myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private boolean ExistDatabase(){
        File myFile=new File(dbPath+dbName);
        return myFile.exists();
    }

    private void CopyDatabase(){
        try{
            InputStream myInput=myContext.getAssets().open(dbName);
            OutputStream myOutput=new FileOutputStream(dbPath+dbName);
            byte[] myBuffer=new byte[1024];
            int length;
            while((length=myInput.read(myBuffer))>0){
                myOutput.write(myBuffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }catch(Exception ex){}

    }



    public void StartWork(){
        dbPath=myContext.getFilesDir().getParent()+"/databases/";
        if(!ExistDatabase()){
            this.getWritableDatabase();
            CopyDatabase();
        }
    }


    public void insertInfo(int posId,String name,String usercode,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("insert into mh_emp(mh_position_ID,mh_name,mh_userCode,mh_passCode)" +
                " values('"+posId+"','"+name+"','"+usercode+"','"+password+"')");
    }


    public void updateInfo(int posId,String name,String usercode,String password,int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update mh_emp set mh_position_ID='"+posId
                +"',mh_name='"+name
                +"',mh_userCode='"+usercode
                +"',mh_passCode='"+password
                +"' where mh_emp_ID='"+id+"'");
    }


    public void deleteInfo(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from mh_emp where mh_emp_ID='"+id+"'");
    }

    public Integer getBranchId(String branchname){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select mh_branch_ID from mh_branch " +
                "where mh_branch_name='"+branchname+"'",null);
        cursor.moveToFirst();
        int branchID=cursor.getInt(0);

        return branchID;
    }


    public void nullBranch(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update mh_branch set mh_setBranch=null");
    }

    public void setBranch(int branchID){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update mh_branch set mh_setBranch=1 where mh_branch_ID='"+branchID+"'");
    }

    public String getBranchName(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select mh_branch_name,mh_branch_ID " +
                "from mh_branch where mh_setBranch =1 ",null);
        cursor.moveToFirst();
        String branchname=cursor.getString(0);

        return branchname;
    }



    public ArrayList<String> getAllPosition(){
        ArrayList<String>list=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();

        try{
            String selectQuery="select mh_description from mh_position";
            Cursor cursor=db.rawQuery(selectQuery,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String branch=cursor.getString(cursor.getColumnIndex("mh_description"));
                    list.add(branch);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public Integer getPositionId(String pos){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select mh_position_ID from mh_position " +
                "where mh_description='"+pos+"'",null);
        cursor.moveToFirst();
        int o=cursor.getInt(0);
        return o;
    }



    public List<employee> getAllemployee() {
        List<employee> list_emp=new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();

        Cursor cursor= db.rawQuery("SELECT * from mh_emp order by mh_name", null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                int branch=cursor.getInt(1);
                String name=cursor.getString(2);
                String usercode=cursor.getString(3);
                String password=cursor.getString(4);

                employee emp= new employee(id, branch, name, usercode, password);

                list_emp.add(emp);


            }while(cursor.moveToNext());
        }
        return list_emp;
    }

    public String[] getPositionID(String usercode){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] ar=new String[4];
        Cursor curs=db.rawQuery("select  mh_emp_ID, mh_position_ID,mh_name,mh_userCode " +
                "from mh_emp where mh_userCode='"+usercode+"'",null);
        curs.moveToFirst();
        ar[0]=curs.getString(0);
        ar[1]=curs.getString(1);
        ar[2]=curs.getString(2);
        ar[3]=curs.getString(3);


        return ar;
    }

    public String getDescription(int posId){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor curs=db.rawQuery("select mh_description from mh_position " +
                "where mh_position_ID='"+posId+"'",null);
        curs.moveToFirst();
        String pos=curs.getString(0);
        return pos;
    }

    public Integer PositionID(String position){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor curs=db.rawQuery("select mh_position_ID from mh_position" +
                " where mh_description='"+position+"'",null);
        curs.moveToFirst();
        int pos=curs.getInt(0);
        return pos;
    }


    public Boolean userPass(String user1, String pass1) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from mh_emp where mh_userCode=?" +
                " and mh_passCode=?", new String[]{user1,pass1});
        if(cursor.getCount()>0) {


            return true;
        }
        else {
            return false;
        }
    }

    public Boolean AdminPass(String pass) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from mh_admin where admin_pass=?", new String[]{pass});
        if(cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

    public String AdminID(String pass) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor curs=db.rawQuery("select admin_ID from mh_admin where admin_pass='"+pass+"'",null);
        curs.moveToFirst();
        String pass1=curs.getString(0);
        return pass1;
    }

    public void updateAdminPass(String newpass,int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update mh_admin set admin_pass='"+newpass+"' where admin_ID='"+id+"'");
    }

    public String getBranch(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select mh_branch_name from mh_branch where mh_setBranch =1 ",null);
        cursor.moveToFirst();
        String branchname=cursor.getString(0);

        return branchname;
    }

    public ArrayList<String> getAll(){
        ArrayList<String>list=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();

        try{
            String selectQuery="select mh_branch_name from mh_branch order by mh_branch_name";
            Cursor cursor=db.rawQuery(selectQuery,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String branch=cursor.getString(cursor.getColumnIndex("mh_branch_name"));
                    list.add(branch);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public Boolean Checkuser(String Usercode){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from mh_emp where mh_userCode=?",new String[]{Usercode});
        if(cursor.getCount()>0)return false;
        else return true;

    }

    public Boolean Checkbranch(String branch){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from mh_branch where mh_branch_name=?",new String[]{branch});
        if(cursor.getCount()>0)return false;
        else return true;

    }

    public void AddBranch(String branchname){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("insert into mh_branch(mh_branch_name) values('"+branchname+"')");
    }

    public void updateBranch(String newbranch,String oldbranch){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update mh_branch set mh_branch_name='"+newbranch
                +"' where mh_branch_name='"+oldbranch+"'");

    }

    public void deleteBranch(String branchname){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from mh_branch where mh_branch_name='"+branchname+"'");

    }

    public String[] branchNameID(){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] ar=new String[2];
        Cursor cursor=db.rawQuery("select mh_branch_ID,mh_branch_name from mh_branch where mh_setBranch =1 ",null);
        cursor.moveToFirst();

        ar[0]=cursor.getString(0);
        ar[1]=cursor.getString(1);
        return ar;

    }

    public int[] getTimeType(int name_id, String date, int pos){
        SQLiteDatabase db=this.getReadableDatabase();

        int[] array=new int[2];
        Cursor curs;
        curs=db.rawQuery(
                "select mt.mh_timeType_ID\n" +
                        " from mh_timeType mt\n" +
                        " where mt.sequence = (\n" +
                        "select mt1.sequence\n" +
                        "from mh_transac mh \n" +
                        "join mh_timeType mt1 on mh.mh_timeType_id = mt1.mh_timeType_id\n" +
                        "where mh.mh_transac_ID = (select max(mh_transac_ID) from mh_transac " +
                        "where mh_emp_id = '"+name_id+"')\n" +

                        " and mh.date ='"+date+"'"+
                        ") + 1",null);

        if(!curs.moveToFirst()) {
            curs = db.rawQuery("select min(mh_timeType_ID) from mh_timeType", null);
            curs.moveToFirst();
        }
        array[0]=curs.getInt(0);

        curs=db.rawQuery("select mh_max_allow from mh_position where mh_position_ID='"+pos+"'",null);
        curs.moveToFirst();
        array[1]=curs.getInt(0);

        return array;
    }

    public void InserTransac(int ids, int bID, int timetype, String time1, String date1){

        SQLiteDatabase db=getReadableDatabase();
        db.execSQL("insert into mh_transac(mh_emp_ID,mh_branch_ID,mh_timeType_ID,time,date) " +
                "values('"+ids+"','"+bID+"','"+timetype+"','"+time1+"','"+date1+"')");

    }

    public String[] getUsercodeName(int ids){
        SQLiteDatabase db=getReadableDatabase();
        String[] ar=new String[2];

        Cursor curs=db.rawQuery("select mh_userCode,mh_name from mh_emp where mh_emp_ID='"+ids+"'",null);
        curs.moveToFirst();
        ar[0]=curs.getString(0);
        ar[1]=curs.getString(1);

        return ar;
    }

    public String getTimetype(int timeId){
        SQLiteDatabase db=getReadableDatabase();
        Cursor curs=db.rawQuery("select time_type from mh_timeType where mh_timeType_ID='"+timeId+"'",null);
        curs.moveToFirst();
        String timeT=curs.getString(0);

        return timeT;
    }

    public void InsertToFinal(String usercode, String timetype, String date, String time, String branch,String comment){

        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("insert into mh_final(emp_userCode,time_type,date,time,branch,comment) " +
                "values('"+usercode+"','"+timetype+"','"+date+"','"+time+"','"+branch+"','"+comment+"')");

    }

    public Integer getCount(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor curs=db.rawQuery("select count(mh_final_ID)  from mh_final",null);
        curs.moveToLast();
        int count=curs.getInt(0);

        return count;
    }

    public void updateFinal(String com, String code, int count){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update mh_final set comment='"+com+"' where emp_userCode='"+code+"'and mh_final_ID='"+count+"'");
    }

    public boolean verifyPassword(String pass){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from mh_admin where admin_pass='"+pass+"'",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            return true;
        }else return false;
    }

    public String getRecipient(){

            SQLiteDatabase db=getReadableDatabase();
            Cursor curs=db.rawQuery("select * from mh_email_recipient",null);
            curs.moveToFirst();
            String recepient=curs.getString(0);
            return recepient;


    }

    public void insertRecepient(String email){
        SQLiteDatabase db=getWritableDatabase();
        try{
            db.execSQL("update mh_email_recipient set recipient='"+email+"'");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }


}

