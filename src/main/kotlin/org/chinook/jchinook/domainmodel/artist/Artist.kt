package org.chinook.jchinook.domainmodel.artist

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.EventHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateMember
import org.chinook.jchinook.application.command.CreateArtistCommand
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
@Entity
@Table(name = "Artist")
class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @AggregateIdentifier
    @Column(name = "ArtistId")
    private val id: Int = 0

    @Column(name = "Name")
    @AggregateMember
    private lateinit var name: String

    @CommandHandler
    constructor(command: CreateArtistCommand) {
        assertNameIsValid(command.name)
        apply(ArtistWasCreatedEvent(command.name))
    }

    private fun assertNameIsValid(name: String) {
        if (name.isEmpty()) {
            throw InvalidArtistName(name)
        }
    }

    @EventHandler
    private fun on(event: ArtistWasCreatedEvent) {
        name = event.name
    }
}