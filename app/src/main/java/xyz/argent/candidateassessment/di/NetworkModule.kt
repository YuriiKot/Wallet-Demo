import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.argent.candidateassessment.balanceRetriever.EtherscanApi
import xyz.argent.candidateassessment.tokenRegistry.EthExplorerApi

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
    single {
        OkHttpClient().newBuilder().addInterceptor(get<HttpLoggingInterceptor>()).build()
    }
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.ethplorer.io/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(EthExplorerApi::class.java)
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.etherscan.io/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(EtherscanApi::class.java)
    }
}
