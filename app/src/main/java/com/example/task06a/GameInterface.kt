package com.example.task06a

interface GameInterface {

    /** This value represents the state of the 'game'.  It should be initialised to 0.<br />
     * The implementation should probably have a private setter (not visible to users).<br />
     */
    val counter: Int

    /** This function will take the parameter oper, and if the value passed is '+',
     * it will increment 'counter' and if the value passed is '-',
     * it will decrement 'counter'<br />     */
    fun buttonPressed(oper: Char): Unit
}