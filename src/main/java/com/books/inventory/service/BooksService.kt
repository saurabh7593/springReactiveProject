package com.books.inventory.service

import com.books.inventory.beans.Book
import reactor.core.publisher.Flux

interface BooksService {
    fun getAllBooks(): Flux<Book>?;

    fun updateTheBook(book: Book): Book;

    fun deleteABook(book: String) :Boolean;

    fun findABook(params:String?): List<Book>;
}