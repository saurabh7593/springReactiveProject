package com.books.inventory.service

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBookType
import com.books.inventory.beans.GoogleBooks
import com.books.inventory.repository.BooksMongoRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder





@Service
class BooksServiceImpl(private val booksRepository : BooksMongoRepository,@Value ("\${google.url}") val apiUrl :String):BooksService{


    override fun getBooksFromGoogleApi(query: String): Flux<GoogleBooks> {
        val restTemplate = RestTemplate()
        val builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("q", query)

        return  Flux.just(restTemplate.getForObject(
                builder.toUriString(),
                HttpMethod.GET,
                GoogleBooks::class.java))
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
        if (findABook(book.id)==null)
            return Mono.just(booksRepository.save(book))
            return Mono.empty();
    }
}