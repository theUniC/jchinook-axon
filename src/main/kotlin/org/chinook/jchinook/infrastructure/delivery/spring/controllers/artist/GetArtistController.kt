package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.queryhandling.QueryGateway
import org.chinook.jchinook.application.query.GetArtistQuery
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.concurrent.Future

@RestController
@Tag(name = "Artist")
class GetArtistController(val queryGateway: QueryGateway) {
    @GetMapping("/artists/{id}", produces = ["application/hal+json"])
    @ApiResponses(
        ApiResponse(description = "The artist", responseCode = "200", content = [Content(mediaType = "application/hal+json", schema = Schema(implementation = ArtistOutputDto::class))]),
        ApiResponse(description = "Artist with given ID was not found", responseCode = "404", content = [Content(mediaType = "application/problem+json", schema = Schema(implementation = Problem::class))])
    )
    @ResponseBody
    fun handleRequest(@PathVariable("id") id: UUID): Future<ResponseEntity<ArtistOutputDto>> =
        queryGateway
            .query(GetArtistQuery(id), ArtistOutputDto::class.java)
            .thenApply { a ->
                a.add(linkTo(methodOn(GetArtistController::class.java).handleRequest(a.id)).withSelfRel())
                a
            }
            .thenApply { a -> ResponseEntity.ok(a) }

    @ExceptionHandler
    fun handleException(ex: AggregateNotFoundException) =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(
                Problem.create()
                    .withTitle("Artist was not found")
                    .withDetail("Artist with ID ${ex.aggregateIdentifier} was not found")
            )

    @QueryMapping
    fun artist(@Argument id: UUID): ArtistOutputDto =
        queryGateway
            .query(GetArtistQuery(id), ArtistOutputDto::class.java)
            .get()
}
