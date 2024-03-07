package com.example.shopkaro.repository.address_repo

import com.example.shopkaro.components.debugLog
import com.example.shopkaro.model_class.Address
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AddNewAddressImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : AddressRepo {

    private val addressReference = fireStore.collection("address")

    override suspend fun addsNewAddress(
        address: Address,
        success: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        addressReference.add(address)
            .addOnSuccessListener {
                debugLog(msg = "Successfully created")
                success()
            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }
    }

    override fun readsDataFromCollection() {
//        addressReference.addSnapshotListener { snapshot, error ->
//            error?.let {
//                debugLog(msg = "Listen failed with cause = $it")
//            }
//            snapshot?.let {
//                for (document in it){
//                    debugLog(msg = "Data = ${document.data}")
//                }
//            }
//        }
    }
}