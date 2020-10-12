package com.example.mytictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myTicTacToe: MyTicTacToe = MyTicTacToe()
        myTicTacToe.initializeGame()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>()
    }
}
