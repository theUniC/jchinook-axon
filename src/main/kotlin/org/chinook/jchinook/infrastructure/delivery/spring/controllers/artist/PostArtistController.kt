package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.commandhandling.gateway.CommandGateway
import org.chinook.jchinook.application.command.CreateArtistCommand
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistInputDto
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
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
    @PostMapping("/artists", consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(
        ApiResponse(description = "Artist was created successfully", responseCode = "201")
    )
    @ResponseBody
    fun handleRequest(@RequestBody artistInputDto: ArtistInputDto): ArtistOutputDto {
        val uuid = UUID.randomUUID()
        commandGateway.sendAndWait<Any>(CreateArtistCommand(uuid, artistInputDto.artistName))
        return ArtistOutputDto(uuid, artistInputDto.artistName)
    }
}
