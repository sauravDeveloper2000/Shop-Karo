package com.example.shopkaro.ui.account_screen.account_section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopkaro.components.debugLog
import com.example.shopkaro.model_class.Address
import com.example.shopkaro.repository.user_account_repo.UserAccountRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val userAccountRepository: UserAccountRepository,
    fireStore: FirebaseFirestore
) : ViewModel() {

    private val collectionReference = fireStore.collection("address")
    lateinit var registration: ListenerRegistration
    var addresses = MutableStateFlow<List<Address>>(emptyList())
        private set

    init {
        getRealTimeUpdates()
    }

    fun signOut() {
        viewModelScope.launch {
            userAccountRepository.signOutUser()
        }
    }

    private fun getRealTimeUpdates() {
        registration = collectionReference.addSnapshotListener { value, error ->
            error?.let {
                debugLog(msg = it.message.toString())
                return@addSnapshotListener
            }
            value?.let {
                val addresses = mutableListOf<Address>()
                for (document in it) {
                    val address = document.toObject<Address>()
                    addresses.add(address)
                }
                debugLog(msg = addresses.toString())
                this.addresses.value = addresses.toList()
            }
        }
    }

    /**
     * deleteAddress() - This function deletes the existing address.
     */
    fun deleteAddress(
        address: Address
    ) {
        viewModelScope.launch {
            val querySnapshot = collectionReference
                .whereEqualTo("userName", address.userName)
                .whereEqualTo("pinCode", address.pinCode)
                .whereEqualTo("city", address.city)
                .whereEqualTo("state", address.state)
                .whereEqualTo("houseNumber", address.houseNumber)
                .whereEqualTo("roadOrArea", address.roadOrArea)
                .whereEqualTo("typeOfAddress", address.typeOfAddress)
                .get()
                .await()
            if (querySnapshot.documents.isNotEmpty()) {
                collectionReference.document(querySnapshot.documents[0].id).delete()
                    .addOnSuccessListener {
                        debugLog(msg = "Deleted successfully")
                    }
                    .addOnFailureListener {
                        debugLog(msg = "Failure occurred")
                    }
            }
        }
    }
}