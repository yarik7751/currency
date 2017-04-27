package by.yarik.currency.util.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import by.yarik.currency.util.api.pojo.Currency;

public class CurrencyDAO  extends BaseDaoImpl<Currency, Integer> {

    private static final String TAG = "CurrencyDAO_logs";

    protected CurrencyDAO(ConnectionSource connectionSource, Class<Currency> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Currency> getAllCurrency() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            Log.d(TAG, "getAllCurrency error: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public List<Currency> getCurrencyByAbbreviation(String abbr) {
        QueryBuilder<Currency, Integer> queryBuilder = queryBuilder();
        try {
            queryBuilder.where().eq("curAbbreviation", abbr);
            PreparedQuery<Currency> preparedQuery = queryBuilder.prepare();
            List<Currency> currencies = query(preparedQuery);
            return currencies;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
