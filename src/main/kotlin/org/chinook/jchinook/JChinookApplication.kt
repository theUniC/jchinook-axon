package org.chinook.jchinook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class JChinookApplication

fun main(args: Array<String>) {
    runApplication<JChinookApplication>(*args)
}
