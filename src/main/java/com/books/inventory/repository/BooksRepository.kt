package com.books.inventory.repository

import com.books.inventory.beans.Book
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class BooksRepository {
    fun getAllBooks(): List<Book> {
        val book1=Book("LearnKotlin", mutableListOf("sauarbh G"),"sssad","Thiss Inr","213",4);
        return listOf<Book>(book1);
    }
}