package com.hf.data.repository

import io.reactivex.Completable
import io.reactivex.Single

interface ICache<Id, Item> {
    fun findItemById(id: Id): Single<Item>
    fun saveItemWithId(id: Id, item: Item): Completable
}