package com.books.inventory.beans

import org.springframework.data.annotation.Id
import java.lang.module.ModuleDescriptor
import java.util.*

data class Book(@Id
                var id: String = UUID.randomUUID().toString(),
                val title: String,
                val authors: List<String>,
                val image: String,
                val description: String,
                val price: String,
                val quantity: Int) {
}