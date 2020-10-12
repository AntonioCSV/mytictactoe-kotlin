package com.example.mytictactoe

class MyTicTacToe {
    private lateinit var board: Array<Array<TicTacToePlayer>>
    private lateinit var history: TicTacToeHistory
    private var _currentPlayer: TicTacToePlayer = TicTacToePlayer.NONE
    var currentPlayer: TicTacToePlayer
        get() = _currentPlayer
        private set(value) { _currentPlayer = value }

    var nextPlayer: TicTacToePlayer = TicTacToePlayer.PLAYER_ONE
        get() {
            return when(currentPlayer) {
                TicTacToePlayer.PLAYER_ONE -> TicTacToePlayer.PLAYER_TWO
                else -> TicTacToePlayer.PLAYER_ONE
            }
        }

    fun initializeGame() {
        initializeBoard()
        history = TicTacToeHistory()
        currentPlayer = nextPlayer
    }

    fun makeNextMove(row: Int, column: Int): Boolean {
        if(board[row-1][column-1] != TicTacToePlayer.NONE) {
            return false;
        }

        board[row-1][column-1] = currentPlayer
        history.pushMove(currentPlayer, row, column)
        currentPlayer = nextPlayer
        return true;
    }

    fun checkState(): TicTacToeState {
        return when(checkWinner()) {
            TicTacToePlayer.PLAYER_ONE -> TicTacToeState.FINISHED_PLAYER_ONE_WON
            TicTacToePlayer.PLAYER_TWO -> TicTacToeState.FINISHED_PLAYER_TWO_WON
            else -> if (checkFullBoard()) TicTacToeState.FINISHED_NO_ONE_WON else TicTacToeState.PLAYING
        }
    }

    fun getPlayerByPosition(row: Int, column: Int): TicTacToePlayer {
        return board[row-1][column-1]
    }
    private fun initializeBoard() {
        board = Array(3) { Array<TicTacToePlayer>(3) { TicTacToePlayer.NONE } }
    }

    private fun checkFullBoard(): Boolean {
        return board.all { row -> row.all { column -> column != TicTacToePlayer.NONE } }
    }

    private fun checkWinner(): TicTacToePlayer {
        var winner: TicTacToePlayer

        for(i in 0..2) {
            winner = checkWinnerOnHorizontal(i)
            if(winner != TicTacToePlayer.NONE) {
                return winner
            }
        }

        for(i in 0..2) {
            winner = checkWinnerOnVertical(i)
            if(winner != TicTacToePlayer.NONE) {
                return winner
            }
        }

        return checkWinnerOnDiagonals()
    }

    private fun checkWinnerOnHorizontal(row: Int): TicTacToePlayer {
        return when {
            board[row][0] == board[row][1] && board[row][0] == board[row][2]-> board[row][0]
            else -> TicTacToePlayer.NONE
        }
    }

    private fun checkWinnerOnVertical(column: Int): TicTacToePlayer {
        return when {
            board[0][column] == board[1][column] && board[0][column] == board[2][column] -> board[0][column]
            else -> TicTacToePlayer.NONE
        }
    }

    private fun checkWinnerOnDiagonals(): TicTacToePlayer {
        return when {
            board[0][0] != TicTacToePlayer.NONE
                    && board[0][0] == board[1][1] && board[0][0] == board[2][2] -> board[0][0]
            board[0][2] != TicTacToePlayer.NONE
                    && board[0][2] == board[1][1] && board[0][2] == board[2][0] -> board[0][2]
            else -> TicTacToePlayer.NONE
        }
    }


}