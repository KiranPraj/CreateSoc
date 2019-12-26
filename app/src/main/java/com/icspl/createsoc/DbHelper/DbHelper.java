package com.icspl.createsoc.DbHelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.icspl.createsoc.DbConstant.DbConstant;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="SOCIETY.db";
    public static final int DBVERSION=1;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DBVERSION);
    }

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @TargetApi(Build.VERSION_CODES.P)
    public DbHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                String.format("CREATE TABLE %s (%s integer PRIMARY KEY AUTOINCREMENT ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) , UNIQUE(%s));", DbConstant.Bill.TABLE_BILL, DbConstant.Bill.ID, DbConstant.Bill.DESCRIPTION, DbConstant.Bill.LEDGER, DbConstant.Bill.GROUP, DbConstant.Bill.EXPENSE_TYPE, DbConstant.Bill.BASIS, DbConstant.Bill.AMOUNT, DbConstant.Bill.DESCRIPTION)
        );
        db.execSQL(
                String.format("CREATE TABLE %s(%s integer PRIMARY KEY AUTOINCREMENT,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) );", DbConstant.SocietyDocuments.TABLE_SOCIETY_DOCUMENT, DbConstant.SocietyDocuments.ID, DbConstant.SocietyDocuments.TITLE, DbConstant.SocietyDocuments.DESCRIPTION, DbConstant.SocietyDocuments.DOCUMENT, DbConstant.SocietyDocuments.PHOTO, DbConstant.SocietyDocuments.NUMBER, DbConstant.SocietyDocuments.SOCIETY_ID)
        );
        db.execSQL(
                String.format("CREATE TABLE %s (%s integer PRIMARY KEY AUTOINCREMENT,%s VARCHAR(450),%s VARCHAR(450),%s VARCHAR(450),%s VARCHAR(1000),%s VARCHAR(1000),%s VARCHAR(1000),%s VARCHAR(1000) ,UNIQUE(%s));",
                        DbConstant.FlatDetails.TABLE_FLAT_DETAILS
                        ,DbConstant.FlatDetails.ID
                        ,DbConstant.FlatDetails.BLOCK_ID,
                        DbConstant.FlatDetails.WING_ID,
                        DbConstant.FlatDetails.FLAT_NAME,
                        DbConstant.FlatDetails.FLAT_NUMBER,
                        DbConstant.FlatDetails.NO_OF_OCCUPANTS,
                        DbConstant.FlatDetails.BUILD_UP_AREA,
                        DbConstant.FlatDetails.CARPET_AREA,
                        DbConstant.FlatDetails.FLAT_NUMBER)
        );
        db.execSQL(
                String.format("CREATE TABLE %s(%s integer PRIMARY KEY AUTOINCREMENT,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) ,%s VARCHAR(1000) );", DbConstant.OwnerDocuments.TABLE_SOCIETY_DOCUMENT, DbConstant.OwnerDocuments.ID, DbConstant.OwnerDocuments.TITLE, DbConstant.OwnerDocuments.DESCRIPTION, DbConstant.OwnerDocuments.DOCUMENT, DbConstant.OwnerDocuments.PHOTO, DbConstant.OwnerDocuments.NUMBER, DbConstant.OwnerDocuments.FLAT_ID)
        );
        db.execSQL(
                "CREATE TABLE "+ DbConstant.OwnerDetails.TABLE_OWNER_DETAILS+"(" +
                        DbConstant.OwnerDetails.ID+" integer PRIMARY KEY  AUTOINCREMENT ,"+
                        DbConstant.OwnerDetails.FID+" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.ONAME +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.ODOB +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.OEMAIL +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.OMOBNO +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.OLANDLINE +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.OADD +" VARCHAR(2000) ,"+
                        DbConstant.OwnerDetails.OAADHAR +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.OPAN +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.ORELATION +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.OTYPE +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.VOTING_RIGHT +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.ATTENDING_MEETING +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.RELATIVE_NAME +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.RELATIVE_NO +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.RELATIVE_LANDLINE +" VARCHAR(450) ,"+
                        DbConstant.OwnerDetails.RELATIVE_EMAIL +" VARCHAR(450) );"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
