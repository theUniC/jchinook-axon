package org.chinook.jchinook.infrastructure.delivery.spring.controllers.artist

import io.swagger.v3.oas.annotations.tags.Tag
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.chinook.jchinook.application.query.GetArtistsQuery
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Optional
import java.util.concurrent.CompletableFuture

@RestController
@Tag(name = "Artist")
class GetArtistsController(val queryGateway: QueryGateway) {
    @GetMapping("/artists", produces = ["application/json"])
    fun handleRequest(@RequestParam offset: Optional<Int>, @RequestParam limit: Optional<Int>): CompletableFuture<ResponseEntity<List<ArtistOutputDto>>> {
        return queryGateway
            .query(GetArtistsQuery(offset.orElse(0), limit.orElse(10)), ResponseTypes.multipleInstancesOf(ArtistOutputDto::class.java))
            .thenApply { a -> ResponseEntity.ok(a) }
    }
}
