package com.books.inventory.service

import com.books.inventory.beans.Book

interface BooksService {
    fun getAllBooks(): List<Book>;

    fun updateTheBook(title:String,book: Book);

    fun deleteABook(book: Book);

    fun findABook(params:String?): List<Book>;
}