package css.cis3334.donovan_chockunit10participation;

/**
 * Created by dchock on 3/31/2017.
 * Creates database
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    //Store table id's in strings
    public static final String TABLE_COMMENTS = "comments"; //declare table id "comments"
    public static final String COLUMN_ID = "_id";// declare table id "_id"
    public static final String COLUMN_COMMENT = "comment";// value of specific comment entered
    public static final String COLUMN_RATING = "rating"; // declare table id "rating

    private static final String DATABASE_NAME = "commments.db"; //Declare table id "comments.db"
    private static final int DATABASE_VERSION = 2; //declare database version => 1

    // Database creation sql statement
    //CREATE TABLE table_name ( column1 datatype, column2 datatype, column3 datatype,....);
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null,"+ COLUMN_RATING + " text value 1-5)";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates MySQLiteHelper database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }


    //deletes all existing data
    //defines constants for table names and columns
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

}