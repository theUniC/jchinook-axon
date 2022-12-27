package org.chinook.jchinook.application.query

import org.axonframework.queryhandling.QueryHandler
import org.chinook.jchinook.domainmodel.artist.ArtistRepository
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.stereotype.Component

@Component
data class GetArtistsQueryHandler(val artistRepository: ArtistRepository) {
    @QueryHandler
    fun handle(query: GetArtistsQuery) =
        artistRepository
            .all(query.offset, query.limit)
            .map { a -> ArtistOutputDto.fromArtist(a) }
}
