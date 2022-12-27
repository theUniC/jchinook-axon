package org.chinook.jchinook.domainmodel.artist

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.EventHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.modelling.command.AggregateLifecycle.markDeleted
import org.axonframework.modelling.command.AggregateMember
import org.axonframework.spring.stereotype.Aggregate
import org.chinook.jchinook.application.command.ChangeArtistNameCommand
import org.chinook.jchinook.application.command.CreateArtistCommand
import org.chinook.jchinook.application.command.RemoveArtistCommand
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Aggregate
@Entity
@Table(name = "Artist")
class Artist {
    @Id
    @AggregateIdentifier
    @Column(name = "ArtistId", length = 36)
    private lateinit var id: String

    @Column(name = "Name")
    @AggregateMember
    private lateinit var name: String

    @CommandHandler
    constructor(command: CreateArtistCommand) {
        assertNameIsValid(command.name)
        apply(ArtistWasCreatedEvent(command.id, command.name))
    }

    fun getId() = UUID.fromString(id)
    fun getName() = name

    @CommandHandler
    fun changeArtistName(command: ChangeArtistNameCommand) {
        assertNameIsValid(command.newArtistName)
        apply(ArtistNameWasChanged(command.artistId, command.newArtistName))
    }

    @CommandHandler
    fun removeArtist(command: RemoveArtistCommand) {
        apply(ArtistWasRemoved(command.artistId))
    }

    private fun assertNameIsValid(name: String) {
        if (name.isEmpty()) {
            throw InvalidArtistName(name)
        }
    }

    @EventHandler
    private fun on(event: ArtistWasCreatedEvent) {
        id = event.id.toString()
        name = event.name
    }

    @EventHandler
    private fun on(event: ArtistNameWasChanged) {
        name = event.newArtistName
    }

    @EventHandler
    private fun on(event: ArtistWasRemoved) {
        markDeleted()
    }
}
