package com.books.inventory.service

import com.books.inventory.beans.Book
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BooksService {
    fun getAllBooks(): Flux<Book>?;

    fun updateTheBook(book: Book): Book;

    fun deleteABook(book: String) : Mono<Boolean>;

    fun findABook(params:String?): Flux<Book>;
}