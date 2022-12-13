package org.chinook.jchinook.application.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ChangeArtistNameCommand(
    @TargetAggregateIdentifier val artistId: Int,
    val newArtistName: String
)
