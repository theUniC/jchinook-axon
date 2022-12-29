package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.chinook.jchinook.application.query.GetArtistsQuery
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.Optional
import java.util.concurrent.Future

@RestController
@Tag(name = "Artist")
class GetArtistsController(val queryGateway: QueryGateway) {
    @GetMapping("/artists", produces = ["application/hal+json"])
    @ApiResponses(
        ApiResponse(description = "The artists collection", content = [Content(mediaType = "application/hal+json", array = ArraySchema(schema = Schema(implementation = ArtistOutputDto::class)))])
    )
    @ResponseBody
    fun handleRequest(@RequestParam offset: Optional<Int>, @RequestParam limit: Optional<Int>): Future<ResponseEntity<CollectionModel<ArtistOutputDto>>> {
        return queryGateway
            .query(GetArtistsQuery(offset.orElse(0), limit.orElse(10)), ResponseTypes.multipleInstancesOf(ArtistOutputDto::class.java))
            .thenApply { `as` ->
                `as`.forEach { a -> a.add(linkTo(methodOn(GetArtistController::class.java).handleRequest(a.id)).withSelfRel()) }
                `as`
            }
            .thenApply { `as` ->
                CollectionModel
                    .of(`as`, linkTo(methodOn(GetArtistsController::class.java).handleRequest(Optional.empty(), Optional.empty())).withSelfRel())
            }
            .thenApply { `as` -> ResponseEntity.ok(`as`) }
    }
}
