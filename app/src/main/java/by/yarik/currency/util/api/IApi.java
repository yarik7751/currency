package by.yarik.currency.util.api;

import java.util.List;
import java.util.Objects;

import by.yarik.currency.util.api.pojo.Currency;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IApi {

    //Атрыманне поўнага пераліка валют
    @GET("Currencies")
    Call<List<Currency>> getAllCurrencies();
}
