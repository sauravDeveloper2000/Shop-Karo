package com.example.shopkaro.di

import android.content.Context
import androidx.room.Room
import com.example.shopkaro.api_service.ProductApiService
import com.example.shopkaro.repository.address_repo.AddNewAddressImpl
import com.example.shopkaro.repository.address_repo.AddressRepo
import com.example.shopkaro.repository.product_repo.ProductRepo
import com.example.shopkaro.repository.product_repo.ProductRepoImpl
import com.example.shopkaro.repository.user_account_repo.UserAccountRepository
import com.example.shopkaro.repository.user_account_repo.UserAccountRepositoryImpl
import com.example.shopkaro.repository.repo_for_db.ProductDbRepo
import com.example.shopkaro.repository.repo_for_db.ProductDbRepoImpl
import com.example.shopkaro.room_database.ProductDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun providesUserAccountRepo(
        firebaseAuth: FirebaseAuth
    ): UserAccountRepository {
        return UserAccountRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun providesRoomDbInstance(
        @ApplicationContext context: Context
    ): ProductDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ProductDatabase::class.java,
            name = "product_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesProductDbRepo(
        db: ProductDatabase
    ): ProductDbRepo {
        return ProductDbRepoImpl(db.productDao)
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesRepositoryInstance(
        productApiService: ProductApiService
    ): ProductRepo {
        return ProductRepoImpl(productApiService)
    }

    @Provides
    @Singleton
    fun providesAddressRepo(
        firebaseFireStore: FirebaseFirestore
    ): AddressRepo {
        return AddNewAddressImpl(
            fireStore = firebaseFireStore
        )
    }
}