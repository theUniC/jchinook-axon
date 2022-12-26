package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.commandhandling.gateway.CommandGateway
import org.chinook.jchinook.application.command.CreateArtistCommand
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.CreateArtistInputDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/artists")
@Tag(name = "Artist")
class PostArtistController(val commandGateway: CommandGateway) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(
        ApiResponse(description = "Artist was created successfully", responseCode = "201"),
    )
    fun handleRequest(@RequestBody createArtistInputDto: CreateArtistInputDto) {
        commandGateway.sendAndWait<Any>(CreateArtistCommand(createArtistInputDto.id, createArtistInputDto.name))
    }
}
