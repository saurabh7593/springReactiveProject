package com.books.inventory.service

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBooks
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.ArrayList

interface BooksService {
    fun getAllBooks(): Flux<Book>?

    fun updateTheBook(book: Book): Mono<Book>

    fun deleteABook(book: String): Mono<Void>

    fun findABook(params:String?): Flux<Book>

    fun getBooksFromGoogleApi (query :String) : Flux<GoogleBooks>

    fun addBooksToInventory(book:Book): Mono<Book>

    fun getAuditReport(): ArrayList<String>?;
}