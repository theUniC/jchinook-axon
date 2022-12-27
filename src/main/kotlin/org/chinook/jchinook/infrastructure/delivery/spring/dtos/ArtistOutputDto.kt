package org.chinook.jchinook.infrastructure.delivery.spring.dtos

import org.chinook.jchinook.domainmodel.artist.Artist
import java.util.UUID

data class ArtistOutputDto(
    val id: UUID,
    val name: String
) {
    companion object {
        fun fromArtist(a: Artist) = ArtistOutputDto(
            id = a.getId(),
            name = a.getName()
        )
    }
}
