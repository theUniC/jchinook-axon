package org.chinook.jchinook.application.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class ChangeArtistNameCommand(
    @TargetAggregateIdentifier val artistId: UUID,
    val newArtistName: String
)
