package org.chinook.jchinook.domainmodel.artist

import java.util.UUID

data class ArtistNameWasChanged(
    val artistId: UUID,
    val newArtistName: String
)
