package com.veeransh.aifashion.enterprise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.veeransh.aifashion.enterprise.data.local.dao.*
import com.veeransh.aifashion.enterprise.data.local.entity.*

@Database(
    entities = [
        ProductEntity::class,
        OrderMasterEntity::class,
        OrderItemEntity::class,
        DealerWalletEntity::class,
        WalletTransactionEntity::class,
        UserEntity::class,
        FinishedGoodsEntity::class,
        AdminConfigEntity::class,
        AiDrapeResultEntity::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderMasterDao(): OrderMasterDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun dealerWalletDao(): DealerWalletDao
    abstract fun userDao(): UserDao
    abstract fun finishedGoodsDao(): FinishedGoodsDao
    abstract fun adminConfigDao(): AdminConfigDao
    abstract fun aiDrapeResultDao(): AiDrapeResultDao
}
