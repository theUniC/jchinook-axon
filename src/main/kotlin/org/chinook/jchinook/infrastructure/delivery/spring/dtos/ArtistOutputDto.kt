package org.chinook.jchinook.infrastructure.delivery.spring.dtos

import org.chinook.jchinook.domainmodel.artist.Artist
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.util.UUID

@Relation(collectionRelation = "artists", itemRelation = "artist")
data class ArtistOutputDto(
    val id: UUID,
    val name: String
) : RepresentationModel<ArtistOutputDto>() {
    companion object {
        fun fromArtist(a: Artist) = ArtistOutputDto(
            id = a.getId(),
            name = a.getName()
        )
    }
}
