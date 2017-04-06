package css.cis3334.donovan_chockunit10participation;

/**
 * Created by dchock on 3/31/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.Rating;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    //string that stores column values, column_id and column_comment
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_COMMENT, MySQLiteHelper.COLUMN_RATING };

    //gets the current state of the database
    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    //opens the database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    //closes database
    public void close() {
        dbHelper.close();
    }

    //declares database field and creates comment
    public Comment createComment(String comment, String rating) {
        ContentValues values = new ContentValues();
        //inserts value into column_comment
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        values.put(MySQLiteHelper.COLUMN_RATING, rating);
        //inserts value into id
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        //allows read/write access
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    //deletes comments and the id assigned to it
    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    //gets array of comments
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        //!cursor.isAfterLast()

        while (cursor.moveToFirst()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }


    //when cursos overlaying the comment, it sets and id and comment to existing value
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        comment.setComment(cursor.getString(1));
        comment.setRating(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_RATING)));
        return comment;
    }
    /*
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    } */
}