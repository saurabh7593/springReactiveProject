package com.books.inventory.service

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBooks
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BooksService {
    fun getAllBooks(): Flux<Book>?

    fun updateTheBook(book: Book): Mono<Book>

    fun deleteABook(book: String) : Mono<Boolean>

    fun findABook(params:String?): Flux<Book>

    fun getBooksFromGoogleApi (query :String) : Flux<GoogleBooks>

    fun addBooksToInventory(book:Book):Mono<Book>
}