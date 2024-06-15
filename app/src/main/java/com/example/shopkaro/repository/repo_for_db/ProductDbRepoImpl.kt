package com.example.shopkaro.repository.repo_for_db

import android.content.Context
import android.widget.Toast
import com.example.shopkaro.room_database.NewProduct
import com.example.shopkaro.room_database.ProductDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDbRepoImpl @Inject constructor(
    private val dao: ProductDao
) : ProductDbRepo {

    override suspend fun updateOrInsertProduct(
        context: Context,
        product: NewProduct,
        msg: String
    ) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
        dao.updateOrInsertProduct(product)
    }

    override fun getProduct(): Flow<List<NewProduct>> = dao.getProduct()

    override suspend fun deleteProduct(product: NewProduct) {
        dao.deleteProduct(product)
    }
}