package com.example.shopkaro.ui.account_screen.account_section

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopkaro.components.VerticalSpace
import com.example.shopkaro.model_class.Address

@Composable
fun AccountScreen(
    modifier: Modifier,
    addNewAddress: () -> Unit,
    accountScreenViewModel: AccountScreenViewModel = hiltViewModel()
) {
    val addresses = accountScreenViewModel.addresses.collectAsState().value
    Column(
        modifier = modifier.scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
         * With this below button user can add new address for themselves.
         */
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                .clickable {
                    addNewAddress()
                },
            text = "+ Add New Address",
            textAlign = TextAlign.Center,
            color = Color.Blue,
            style = MaterialTheme.typography.headlineSmall
        )
        Divider(
            modifier = Modifier.padding(horizontal = 8.dp),
            thickness = 4.dp,
            color = Color.Gray
        )
        Text(
            text = "${addresses.size}  Save Addresses",
            style = MaterialTheme.typography.titleMedium
        )

        /**
         * With below lazy column layout we are showing user addresses to them in account screen.
         */
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(
                items = addresses
            ) { address ->
                UserAddress(
                    address = address,
                    modifier = Modifier.fillMaxWidth(),
                    accountScreenViewModel = accountScreenViewModel
                )
                VerticalSpace(space = 15)
            }
        }
        /**
         * Below button can cause user to sign out from the app.
         */
        Button(
            onClick = {
                accountScreenViewModel.signOut()
            },
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Sign Out",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * UserAddress() - This composable creates new address element which is show to user in account screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAddress(
    address: Address,
    modifier: Modifier = Modifier,
    accountScreenViewModel: AccountScreenViewModel
) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /**
             * Name
             */
            Text(
                text = "Name:- ${address.userName}",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
            /**
             * Trash Can Icon to delete address.
             */
            IconButton(onClick = {
                accountScreenViewModel.deleteAddress(address)
                Toast.makeText(context, "Address Deleted successfully", Toast.LENGTH_SHORT).show()

            }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            text = "PinCode:- ${address.pinCode}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 2.dp),
            text = "City:- ${address.city}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 2.dp),
            text = "State:- ${address.state}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 2.dp),
            text = "House Number:- ${address.houseNumber}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 2.dp),
            text = "RoadOrArea:- ${address.roadOrArea}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400
        )
        FilterChip(
            modifier = Modifier.padding(horizontal = 5.dp),
            selected = true,
            onClick = { /*TODO*/ },
            leadingIcon = {
                if (address.typeOfAddress.contains("Home")) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Apartment,
                        contentDescription = "Apartment"
                    )
                }
            },
            label = {
                Text(text = "${address.typeOfAddress}")
            },
            shape = RoundedCornerShape(5.dp)
        )
    }
}