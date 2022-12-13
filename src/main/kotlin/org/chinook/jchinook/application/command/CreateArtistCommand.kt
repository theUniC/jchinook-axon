package org.chinook.jchinook.application.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateArtistCommand(
    val name: String
)