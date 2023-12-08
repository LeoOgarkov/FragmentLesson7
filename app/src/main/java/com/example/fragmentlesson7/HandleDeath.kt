package com.example.fragmentlesson7

interface HandleDeath {
    fun firstOpening()
    fun wasDeathHappened():Boolean
    fun deathHandled()

    class Base:HandleDeath {
        private var deathHappened = true

        override fun firstOpening() {
            deathHappened = false
        }

        override fun wasDeathHappened(): Boolean {
            return  deathHappened
        }

        override fun deathHandled() {
            deathHappened = false
        }

    }
}