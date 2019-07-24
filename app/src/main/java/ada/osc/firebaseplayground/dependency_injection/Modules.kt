

import ada.osc.firebaseplayground.BuildConfig
import ada.osc.firebaseplayground.MainViewModel
import ada.osc.firebaseplayground.interactor.Interactor
import com.example.app_home.common.rest_interface.RestInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://fir-playground-16a11.firebaseio.com/"
const val KEY_AUTHORIZATION = "authorization"
const val AUTHORIZATION_VALUE = "test"
const val LOGGING_INTERCEPTOR = "logging"
const val AUTH_INTERCEPTOR = "auth"
private const val CONNECTION_TIMEOUT = 30000L


val mainModule = module {
        factory { provideMealRestInterface(get()) }
        factory { Interactor(get()) }
        viewModel { MainViewModel(get()) }

}

private fun provideMealRestInterface(retrofit: Retrofit): RestInterface =
    retrofit.create(RestInterface::class.java)



val networkingModule = module {

    //INTERCEPTORS
    single(named(LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single(named(AUTH_INTERCEPTOR)) {
        Interceptor {
            val request = it.request().newBuilder().addHeader(KEY_AUTHORIZATION, AUTHORIZATION_VALUE).build()
            it.proceed(request)
        }
    }

    //OKHTTPCLIENT
    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(get(named(LOGGING_INTERCEPTOR)))
            readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        }.build()

    }

    //RETROFIT
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}
