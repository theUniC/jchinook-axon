package org.chinook.jchinook.infrastructure.delivery.spring.controllers

import org.axonframework.commandhandling.gateway.CommandGateway
import org.chinook.jchinook.application.command.CreateArtistCommand
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/artists")
class PostArtistController(val commandGateway: CommandGateway) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun handleRequest(@RequestBody artistDto: ArtistDto) {
        commandGateway.sendAndWait<Any>(CreateArtistCommand(artistDto.name))
    }
}
