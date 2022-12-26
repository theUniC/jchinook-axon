package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.commandhandling.gateway.CommandGateway
import org.chinook.jchinook.application.command.ChangeArtistNameCommand
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.UpdateArtistInputDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Tag(name = "Artist")
class PutArtistController(val commandGateway: CommandGateway) {
    @PutMapping("/artist/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun handleRequest(@PathVariable(name = "id") id: UUID, @RequestBody artistDto: UpdateArtistInputDto) {
        commandGateway.sendAndWait<Any>(ChangeArtistNameCommand(id, artistDto.artistName))
    }
}
