package com.example.guessgame

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val words = listOf("Мышара", "Петушара", "Гаглюся")
    val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    init {
        secretWordDisplay = deriveSecretWordDisplaying()
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
                secretWordDisplay = deriveSecretWordDisplaying()
            } else {
                incorrectGuesses += guess
                livesLeft--
            }
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay, true)

    fun isLost() = livesLeft <= 0

    fun wonLostMessage(): String{
        var message = ""
        if(isWon()) message = "You won"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }
}