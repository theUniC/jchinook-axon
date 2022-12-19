package org.chinook.jchinook.application.command

import java.util.UUID

data class CreateArtistCommand(
    val id: UUID,
    val name: String
)
