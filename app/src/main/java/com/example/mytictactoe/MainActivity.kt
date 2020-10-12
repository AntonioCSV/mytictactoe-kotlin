package com.example.mytictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mytictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var game: MyTicTacToe
    private lateinit var binding: ActivityMainBinding
    private lateinit var boxes: ArrayList<TextView>
    private var gameFinished: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        initBoxes()
        initializeGameListeners()
        game = MyTicTacToe()
        game.initializeGame()
    }

    private fun initBoxes() {
        boxes = arrayListOf(
            binding.boxOneOne, binding.boxOneTwo, binding.boxOneThree,
            binding.boxTwoOne, binding.boxTwoTwo, binding.boxTwoThree,
            binding.boxThreeOne, binding.boxThreeTwo, binding.boxThreeThree
        )
    }

    private fun getCoordinates(view: View): Pair<Int, Int>? {
        return when(view) {
            binding.boxOneOne -> Pair(1, 1)
            binding.boxOneTwo -> Pair(1, 2)
            binding.boxOneThree -> Pair(1, 3)
            binding.boxTwoOne -> Pair(2, 1)
            binding.boxTwoTwo -> Pair(2, 2)
            binding.boxTwoThree -> Pair(2, 3)
            binding.boxThreeOne -> Pair(3, 1)
            binding.boxThreeTwo -> Pair(3, 2)
            binding.boxThreeThree -> Pair(3, 3)
            else -> null
        }
    }

    private fun initializeGameListeners() {
        for(box in boxes) {
            box.setOnClickListener { view -> makeMove(view) }
        }

        binding.playAgainButton.setOnClickListener { resetGame() }
    }

    private fun makeMove(view: View) {
        if(gameFinished) {
            return
        }

        val coordinates = getCoordinates(view)

        if(game.makeNextMove(coordinates!!.first, coordinates.second)) {
            updateBoxes()
            checkFinishGame()
        }
    }

    private fun updateBoxes() {
        var coordinates: Pair<Int, Int>?
        var player: TicTacToePlayer

        for(box in boxes) {
            coordinates = getCoordinates(box)
            player = game.getPlayerByPosition(coordinates!!.first, coordinates!!.second)
            box.text = when (player.mark) {
                null -> ""
                else -> player.mark.toString()
            }
        }
    }

    private fun resetGame() {
        this.gameFinished = false;
        binding.playAgainButton.visibility = View.GONE
        binding.gameFinishedText.visibility = View.GONE
        game.initializeGame()
        updateBoxes()
    }

    private fun checkFinishGame() {
        val gameState = game.checkState()
        if(gameState != TicTacToeState.PLAYING) {
            val messageGameFinished = when(gameState) {
                TicTacToeState.FINISHED_PLAYER_ONE_WON -> getString(R.string.xptoplayer_won, TicTacToePlayer.PLAYER_ONE.mark)
                TicTacToeState.FINISHED_PLAYER_TWO_WON -> getString(R.string.xptoplayer_won, TicTacToePlayer.PLAYER_TWO.mark)
                else -> getString(R.string.game_over_nobody_won)
            }

            binding.gameFinishedText.text = messageGameFinished
            this.gameFinished = true;
            binding.gameFinishedText.visibility = View.VISIBLE
            binding.playAgainButton.visibility = View.VISIBLE
        }
    }
}
