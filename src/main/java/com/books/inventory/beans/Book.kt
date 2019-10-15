package com.books.inventory.beans

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "book")
data class Book(@Id
                var id: String = UUID.randomUUID().toString(),
                val title: String,
                val authors: List<String>?,
                val imageLinks: ImageLinks?,
                val description: String?,
                val price: String?,
                val quantity: String?)

data class ImageLinks(val smallThumbnail: String?, val thumbnail: String?)

data class GoogleBooks(val items: List<GoogleBookType?>)

data class GoogleBookType(val volumeInfo: Book?)
