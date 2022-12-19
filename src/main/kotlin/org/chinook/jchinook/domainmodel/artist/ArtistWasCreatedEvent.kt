package org.chinook.jchinook.domainmodel.artist

import java.util.UUID

data class ArtistWasCreatedEvent(
    val id: UUID,
    val name: String
)
