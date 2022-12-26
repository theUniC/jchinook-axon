package org.chinook.jchinook.application.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class RemoveArtistCommand(
    @TargetAggregateIdentifier val artistId: UUID
)
