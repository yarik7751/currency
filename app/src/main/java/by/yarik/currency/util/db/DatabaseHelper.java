package by.yarik.currency.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import by.yarik.currency.util.api.pojo.Currency;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "DatabaseHelper_logs";

    private static final String DATABASE_NAME ="currency_yarik.db";

    private static final int DATABASE_VERSION = 1;

    private CurrencyDAO currencyDAO = null;

    public CurrencyDAO getCurrencyDAO()  {
        if(currencyDAO == null) {
            try {
                currencyDAO = new CurrencyDAO(getConnectionSource(), Currency.class);
            } catch (SQLException e) {e.printStackTrace();}
        }
        return currencyDAO;
    }

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Currency.class);
            Log.d(TAG, "CREATE DB");
        } catch (SQLException e) {
            Log.d(TAG, "error creating DB " + DATABASE_NAME + ": " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {}

    @Override
    public void close() {
        super.close();
        currencyDAO = null;
    }
}
