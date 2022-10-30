package com.kolushkin.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kolushkin.shoppinglist.data.ShopListRepositoryDbImpl
import com.kolushkin.shoppinglist.domain.DeleteShopItemUseCase
import com.kolushkin.shoppinglist.domain.EditShopItemUseCase
import com.kolushkin.shoppinglist.domain.GetShopItemListUseCase
import com.kolushkin.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ShopListRepositoryDbImpl(application)
    //private val repository = ShopListRepositoryImpl

    private  val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private  val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private  val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopItemListUseCase.getShopItemList()

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(newItem)
        }
    }

    fun deleteShopList(shopItem: ShopItem){
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }
}