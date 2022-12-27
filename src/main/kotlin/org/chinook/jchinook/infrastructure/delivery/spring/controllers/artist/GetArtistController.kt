package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.queryhandling.QueryGateway
import org.chinook.jchinook.application.query.GetArtistQuery
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.concurrent.CompletableFuture

@RestController
@Tag(name = "Artist")
class GetArtistController(val queryGateway: QueryGateway) {
    @GetMapping("/artists/{id}", produces = ["application/json"])
    fun handleRequest(@PathVariable("id") id: UUID): CompletableFuture<ResponseEntity<ArtistOutputDto>> =
        queryGateway
            .query(GetArtistQuery(id), ArtistOutputDto::class.java)
            .thenApply { a -> ResponseEntity.ok(a) }

    @ExceptionHandler
    fun handleException(ex: AggregateNotFoundException) = ResponseEntity.notFound().build<String>()
}
