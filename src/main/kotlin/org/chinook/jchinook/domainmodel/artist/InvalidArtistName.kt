package org.chinook.jchinook.domainmodel.artist

class InvalidArtistName : RuntimeException {
    constructor(name: String): super("Invalid artist name provided: \"$name\"")
}