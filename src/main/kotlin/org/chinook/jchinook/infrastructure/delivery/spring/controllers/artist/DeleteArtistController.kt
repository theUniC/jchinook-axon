package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.command.AggregateNotFoundException
import org.chinook.jchinook.application.command.RemoveArtistCommand
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Tag(name = "Artist")
class DeleteArtistController(val commandGateway: CommandGateway) {
    @DeleteMapping("/artist/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(
        ApiResponse(description = "The artist was deleted successfully", responseCode = "200"),
        ApiResponse(description = "Artist with given ID was not found", responseCode = "404", content = [Content(mediaType = "application/problem+json", schema = Schema(implementation = Problem::class))])
    )
    fun handleRequest(@PathVariable("id") id: UUID) {
        commandGateway.sendAndWait<Any>(RemoveArtistCommand(id))
    }

    @ExceptionHandler(AggregateNotFoundException::class)
    fun handleException(ex: AggregateNotFoundException) =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(
                Problem.create()
                    .withTitle("Artist was not found")
                    .withDetail("Artist with ID ${ex.aggregateIdentifier} was not found")
            )
}
