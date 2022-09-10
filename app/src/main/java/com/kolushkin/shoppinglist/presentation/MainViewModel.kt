package com.kolushkin.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kolushkin.shoppinglist.data.ShopListRepositoryImpl
import com.kolushkin.shoppinglist.domain.DeleteShopItemUseCase
import com.kolushkin.shoppinglist.domain.EditShopItemUseCase
import com.kolushkin.shoppinglist.domain.GetShopItemListUseCase
import com.kolushkin.shoppinglist.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private  val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private  val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private  val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopItemListUseCase.getShopItemList()

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }

    fun deleteShopList(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }
}