package com.example.task06a

class ButtonGame: GameInterface {

    override var counter: Int = 0

    override fun buttonPressed(oper: Char): Unit {
        when (oper) {
            'p'  -> counter = counter + 1
            'm'  -> counter = counter - 1
            else -> throw IllegalArgumentException("$oper is not a valid button to press")
        }
    }
}