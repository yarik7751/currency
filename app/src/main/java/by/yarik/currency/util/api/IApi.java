package by.yarik.currency.util.api;

import java.util.List;
import java.util.Objects;

import by.yarik.currency.util.api.pojo.Currency;
import by.yarik.currency.util.api.pojo.CurrencyRate;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApi {

    //Атрыманне поўнага пераліка валют
    @GET("Currencies")
    Call<List<Currency>> getAllCurrencies();

    //Атрыманне афіцыйнага курсу беларускага рубля ў адносінах да замежных валют
    @GET("Rates")
    Call<List<CurrencyRate>> getRates(
            @Query ("onDate") String onDate,
            @Query("Periodicity") Integer periodicity,
            @Query ("ParamMode") Integer paramMode
    );

    //Атрыманне афіцыйнага курсу беларускага рубля ў адносінах да замежных валют на сёння
    @GET("Rates")
    Call<List<CurrencyRate>> getRates(
            @Query("Periodicity") Integer periodicity,
            @Query ("ParamMode") Integer paramMode
    );

    @GET("Rates/Dynamics/{curId}")
    Call<List<CurrencyRate>> getDynamic(
            @Path("curId") String curId,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );
}
