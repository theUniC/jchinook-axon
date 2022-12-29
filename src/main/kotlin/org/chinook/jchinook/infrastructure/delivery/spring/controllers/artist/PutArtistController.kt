package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.command.AggregateNotFoundException
import org.chinook.jchinook.application.command.ChangeArtistNameCommand
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistInputDto
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Tag(name = "Artist")
class PutArtistController(val commandGateway: CommandGateway) {
    @PutMapping("/artist/{id}", consumes = ["application/json"], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(
        ApiResponse(description = "The updated artist", responseCode = "200", content = [Content(mediaType = "application/hal+json", schema = Schema(implementation = ArtistOutputDto::class))]),
        ApiResponse(description = "Artist with given ID was not found", responseCode = "404", content = [Content(mediaType = "application/problem+json", schema = Schema(implementation = Problem::class))])
    )
    @ResponseBody
    fun handleRequest(@PathVariable(name = "id") id: UUID, @RequestBody artistDto: ArtistInputDto): ArtistOutputDto {
        commandGateway.sendAndWait<Any>(ChangeArtistNameCommand(id, artistDto.artistName))

        val response = ArtistOutputDto(id, artistDto.artistName)
        response.add(linkTo(methodOn(GetArtistController::class.java).handleRequest(id)).withSelfRel())

        return response
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

    @MutationMapping
    fun updateArtist(@Argument id: UUID, @Argument artistName: String): ArtistOutputDto {
        commandGateway.sendAndWait<Any>(ChangeArtistNameCommand(id, artistName))
        return ArtistOutputDto(id, artistName)
    }
}
