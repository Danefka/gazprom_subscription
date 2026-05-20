package by.danefka.gazprom_subscription

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GazpromSubscriptionApplication

fun main(args: Array<String>) {
    runApplication<GazpromSubscriptionApplication>(*args)
}
