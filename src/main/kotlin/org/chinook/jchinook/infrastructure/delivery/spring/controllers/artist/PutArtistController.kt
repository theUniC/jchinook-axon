package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.command.AggregateNotFoundException
import org.chinook.jchinook.application.command.ChangeArtistNameCommand
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistInputDto
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.http.HttpStatus
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
    @PutMapping("/artist/{id}", consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun handleRequest(@PathVariable(name = "id") id: UUID, @RequestBody artistDto: ArtistInputDto): ArtistOutputDto {
        commandGateway.sendAndWait<Any>(ChangeArtistNameCommand(id, artistDto.artistName))
        return ArtistOutputDto(id, artistDto.artistName)
    }

    @ExceptionHandler(AggregateNotFoundException::class)
    fun handleException(ex: AggregateNotFoundException) = ResponseEntity.notFound().build<String>()
}
