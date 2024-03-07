package com.example.shopkaro.repository.address_repo

import com.example.shopkaro.model_class.Address

/**
 * Below suspending function adds the new address to firestore.
 */
interface AddressRepo {

    suspend fun addsNewAddress(
        address: Address,
        success: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun readsDataFromCollection()
}