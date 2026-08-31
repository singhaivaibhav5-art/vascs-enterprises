package com.veeransh.aifashion.enterprise.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val sku: String,
    val barcode: String,
    val category: String,
    val subCategory: String = "",
    val brand: String,
    val fabric: String,
    val colour: String,
    val size: String,
    val hsn: String,
    val gst: Double,
    val purchasePrice: Double,
    val wholesalePrice: Double,
    val retailPrice: Double,
    val dealerPrice: Double = 0.0,
    val partnerPrice: Double = 0.0,
    val mrp: Double,
    val discount: Double,
    val stock: Int,
    val moq: Int = 1,
    val lowStockAlert: Int = 5,
    val weight: String = "",
    val location: String = "",
    val image: String,
    val imagesJson: String = "[]",
    val supplierName: String = "",
    val description: String = "",
    val tags: String = "",
    val createdAt: String
)
