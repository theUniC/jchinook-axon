package org.chinook.jchinook.domainmodel.artist

class InvalidArtistId : RuntimeException {
    constructor(id: Int) : super("Invalid artist id provided: $id")
}
