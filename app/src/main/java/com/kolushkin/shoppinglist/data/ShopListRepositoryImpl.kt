package com.kolushkin.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kolushkin.shoppinglist.domain.ShopItem
import com.kolushkin.shoppinglist.domain.ShopListRepository
import kotlinx.coroutines.*

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({ p0, p1 -> p0.id.compareTo(p1.id) })
    private var autoIncrementId = 0

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        scope.launch {
            for (i in 0 until 10) {
                val item = ShopItem("Name $i", i, true)
                addShopItem(item)
            }
            scope.cancel()
        }
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        deleteShopItem(oldElement)
        addShopItem(shopItem)
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    private fun updateList(){
        shopListLD.value = shopList.toList()
    }
}