package com.example.shopkaro.model_class

/**
 * Address data class is representing user address.
 */
data class Address(
    val userName: String,
    val pinCode: String,
    val city: String,
    val state: String,
    val houseNumber: String,
    val roadOrArea: String,
    val typeOfAddress: String
) {
    constructor() : this("", "", "", "", "", "", "")
}