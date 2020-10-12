package com.example.mytictactoe

class TicTacToeHistory {
    private val moves = mutableListOf<Triple<TicTacToePlayer, Int, Int>>()

    fun pushMove(player: TicTacToePlayer, row: Int, column: Int) {
        moves.add(Triple(player, row, column))
    }

    fun popMove(): Triple<TicTacToePlayer, Int, Int> {
        return moves.removeAt(moves.count() - 1)
    }
}