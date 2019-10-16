package com.books.inventory.service

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBookType
import com.books.inventory.beans.GoogleBooks
import com.books.inventory.repository.BooksMongoRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder





@Service
class BooksServiceImpl(private val booksRepository : BooksMongoRepository,@Value ("\${google.url}") val apiUrl :String):BooksService{


    override fun getBooksFromGoogleApi(query: String): Flux<GoogleBooks> {
        val builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("q", query)
                .encode().toUriString()

        return WebClient.create(builder)
                .get()
                .retrieve()
                .bodyToFlux(GoogleBooks::class.java)

    }

    override fun getAllBooks(): Flux<Book>? {
        return Flux.fromIterable(booksRepository.findAll())
    }

    override fun updateTheBook(book: Book): Mono<Book>{
        booksRepository.deleteById(book.id)
        return Mono.just(booksRepository.save(book))
    }

    override fun deleteABook(book: String): Mono<Boolean> {
        booksRepository.deleteById(book)
        return Mono.just(true)

    }

    override fun findABook(params: String?): Flux<Book> {
        return Flux.fromIterable(booksRepository.findAll().filter { book->
            book.title.equals(params!!.toUpperCase())|| book.authors?.contains(params!!.toUpperCase())!!
         })
    }

    override fun addBooksToInventory(book: Book): Mono<Book> {
            return Mono.just(booksRepository.save(book))
    }
}