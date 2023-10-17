package models

import java.util.concurrent.atomic.AtomicInteger

object CountingModel {
    private val count = new AtomicInteger()

    def click():Int = {
        count.incrementAndGet()
    }
}