package com.example.guessgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val words = listOf("Мышара", "Петушара", "Гаглюся")
    val secretWord = words.random().uppercase()
    val secretWordDisplay = MutableLiveData<String>()
    val incorrectGuesses = MutableLiveData<String>("")
    val livesLeft = MutableLiveData<Int>(8)

    var correctGuesses = ""

    init {
        secretWordDisplay.value = deriveSecretWordDisplaying()
    }


    private fun deriveSecretWordDisplaying(): String {
        var display = ""
        secretWord.forEach { display += checkLetter(it.toString()) }
        return display
    }

    private fun checkLetter(s: String) = when (correctGuesses.contains(s)) {
        true -> s
        false -> "_"
    }

    fun makeGuess(guess: String){
        if(guess.length == 1){
            if(secretWord.contains(guess)){
                correctGuesses += guess
                secretWordDisplay.value = deriveSecretWordDisplaying()
            } else {
                incorrectGuesses.value?.plus(guess)
                livesLeft.value?.minus(1)
            }
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    fun isLost() = (livesLeft.value ?: 0) <= 0

    fun wonLostMessage(): String{
        var message = ""
        if(isWon()) message = "You won"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }
}