package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.commandhandling.gateway.CommandGateway
import org.chinook.jchinook.application.command.CreateArtistCommand
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistInputDto
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Tag(name = "Artist")
class PostArtistController(val commandGateway: CommandGateway) {
    @PostMapping("/artists", consumes = ["application/json"], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(
        ApiResponse(description = "Artist was created successfully", responseCode = "201")
    )
    @ResponseBody
    fun handleRequest(@RequestBody artistInputDto: ArtistInputDto): ArtistOutputDto {
        val uuid = UUID.randomUUID()
        commandGateway.sendAndWait<Any>(CreateArtistCommand(uuid, artistInputDto.artistName))

        val response = ArtistOutputDto(uuid, artistInputDto.artistName)
        response.add(linkTo(methodOn(GetArtistController::class.java).handleRequest(uuid)).withSelfRel())

        return response
    }

    @MutationMapping
    fun createArtist(@Argument artistName: String): ArtistOutputDto {
        val uuid = UUID.randomUUID()
        commandGateway.sendAndWait<Any>(CreateArtistCommand(uuid, artistName))

        return ArtistOutputDto(uuid, artistName)
    }
}
