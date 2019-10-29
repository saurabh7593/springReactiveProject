package com.books.inventory.service

import com.books.inventory.beans.Book
import org.springframework.stereotype.Service
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import java.time.Instant
import java.util.*


@Service
class KafkaService(private val kafkaTemplate: KafkaTemplate<String,String> ){
    companion object {
        val ReportList = ArrayList<String>()
    }

    fun sendBookIsSavedMessage(book: Book) {
        kafkaTemplate.send("books", "The ${book.toString()} was SAVED at ${Instant.now().toString()}")
    }

    fun sendBookisDeletedMessage(book: Book?) {
        kafkaTemplate.send("books", "The ${book.toString()} was DELETED at ${Instant.now().toString()}")
    }
    fun sendBookisUpdatedMessage(book: Book) {
        kafkaTemplate.send("books", "The ${book.toString()} was UPDATED at ${Instant.now().toString()}")
    }

    @KafkaListener(topics = ["books"], groupId = "group_id")
    fun consume(message: String) {
        ReportList.add(message)
    }
}
