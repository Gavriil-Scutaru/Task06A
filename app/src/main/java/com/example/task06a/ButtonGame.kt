package com.example.task06a

class ButtonGame: GameInterface {

    override var counter: Int = 0

    override fun buttonPressed(oper: Char): Unit {
        when (oper) {
            'p'  -> counter = counter + 1
            'm'  -> counter = counter - 1
            else -> throw IllegalArgumentException("$oper is not a valid button to press")
        }
        fireGameChange()
    }

    private val gameChangeListeners = mutableListOf<GameInterface.GameChangeListener>()

    override fun addGameChangeListener(listener: GameInterface.GameChangeListener) {
        if (listener !in gameChangeListeners) {
            gameChangeListeners.add(listener)
        }
    }

    override fun removeGameChangeListener(listener: GameInterface.GameChangeListener) {
        gameChangeListeners.remove(listener)
    }

    /**
     * This will execute the onGameChange method on all listeners (like the ButtonView).
     */
    private fun fireGameChange() {
        for(listener in gameChangeListeners) {
            listener.onGameChange(this)
        }
    }

}