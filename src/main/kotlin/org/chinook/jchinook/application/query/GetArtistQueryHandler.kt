package org.chinook.jchinook.application.query

import org.axonframework.modelling.command.Repository
import org.axonframework.queryhandling.QueryHandler
import org.chinook.jchinook.domainmodel.artist.Artist
import org.chinook.jchinook.infrastructure.delivery.spring.dtos.ArtistOutputDto
import org.springframework.stereotype.Component

@Component
class GetArtistQueryHandler(private val artistRepository: Repository<Artist>) {
    @QueryHandler
    fun handle(query: GetArtistQuery): ArtistOutputDto =
        artistRepository
            .load(query.artistId.toString())
            .invoke { a -> ArtistOutputDto.fromArtist(a) }
}
