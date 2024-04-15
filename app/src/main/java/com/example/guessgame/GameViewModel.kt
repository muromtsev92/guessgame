package com.example.guessgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private val words = listOf("Мышара", "Петушара", "Гаглюся")
    private val secretWord = words.random().uppercase()
    private var correctGuesses = ""
    private val _secretWordDisplay = MutableLiveData<String>()
    private val _incorrectGuesses = MutableLiveData<String>("")
    private val _livesLeft = MutableLiveData<Int>(8)
    private val _gameOver = MutableLiveData<Boolean>(false)

    val livesLeft: LiveData<Int>
        get() = _livesLeft
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        _secretWordDisplay.value = deriveSecretWordDisplaying()
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
                _secretWordDisplay.value = deriveSecretWordDisplaying()
            } else {
                _incorrectGuesses.value += "$guess"
                _livesLeft.value = livesLeft.value?.minus(1)
            }
        }
        if(isWon() || isLost()) _gameOver.value = true
    }

    private fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    private fun isLost() = (livesLeft.value ?: 0) <= 0

    fun wonLostMessage(): String{
        var message = ""
        if(isWon()) message = "You won"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }
}