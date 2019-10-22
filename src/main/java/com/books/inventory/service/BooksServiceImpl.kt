package com.books.inventory.service

import com.books.inventory.beans.Book
import com.books.inventory.beans.GoogleBooks
import com.books.inventory.repository.BooksMongoRepository

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.toMono


@Service
class BooksServiceImpl(private val booksRepository : BooksMongoRepository,
                       private val kafkaService: KafkaService,
                       @Value ("\${google.url}") val apiUrl :String):BooksService{


    override fun getBooksFromGoogleApi(query: String): Flux<GoogleBooks> {
        val builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .replaceQueryParam("q", query)
                .encode().toUriString()

        return WebClient.create(builder)
                .get()
                .retrieve()
                .bodyToFlux(GoogleBooks::class.java)

    }

    override fun getAllBooks(): Flux<Book> = booksRepository.findAll()

    override fun updateTheBook(book: Book): Mono<Book> {
        booksRepository.deleteById(book.id)
        val updatedBook=booksRepository.save(book)
        updatedBook.subscribe(kafkaService::sendBookisUpdatedMessage)
        return updatedBook
    }

    override fun deleteABook(book: String): Mono<Void> {
        return booksRepository.deleteById(book);
//        deletedBook.subscribe(KafkaService::sendBookisDeletedMessage)
//        return booksRepository.deleteById(book)
    }

    override fun findABook(params: String?): Flux<Book> =booksRepository.findByAuthorsLike(params).
                                                        concatWith(booksRepository.findByTitleLike(params))

    override fun addBooksToInventory(book: Book): Mono<Book> {
        val savedBook=booksRepository.save(book)
        savedBook.subscribe(kafkaService::sendBookIsSavedMessage)
        return savedBook
    }

    override fun getAuditReport() = KafkaService.ReportList

}
