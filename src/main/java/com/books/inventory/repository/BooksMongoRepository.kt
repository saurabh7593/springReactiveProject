package com.books.inventory.repository

import com.books.inventory.beans.Book
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface BooksMongoRepository : ReactiveMongoRepository<Book,String> {
    fun findByTitleLike(query: String?): Flux<Book>
    fun findByAuthorsLike(query: String?): Flux<Book>
}