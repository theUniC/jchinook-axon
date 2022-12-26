package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.commandhandling.gateway.CommandGateway
import org.chinook.jchinook.application.command.RemoveArtistCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Tag(name = "Artist")
class DeleteArtistController(val commandGateway: CommandGateway) {
    @DeleteMapping("/artist/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun handleRequest(@PathVariable("id") id: UUID) {
        commandGateway.sendAndWait<Any>(RemoveArtistCommand(id))
    }
}