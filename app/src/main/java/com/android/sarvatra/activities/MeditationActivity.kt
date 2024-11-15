package com.android.sarvatra.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.sarvatra.R
import com.android.sarvatra.databinding.ActivityMeditationBinding
import com.android.sarvatra.utils.PreferencesHelper
import com.android.sarvatra.utils.isNightTime

class MeditationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeditationBinding
    private lateinit var prefsHelper: PreferencesHelper
    private lateinit var countDownTimer: CountDownTimer

    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMeditationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefsHelper = PreferencesHelper(this)
        setupUI()
    }

    private fun setupUI() {
        val user = prefsHelper.getUserFromPreferences()
        if (isNightTime()) {
            binding.root.setBackgroundResource(R.drawable.night_background)
            binding.sunAnimation.visibility = View.INVISIBLE
            binding.nightAnimation.visibility = View.VISIBLE
        } else {
            binding.root.setBackgroundResource(R.drawable.app_background)
            binding.sunAnimation.visibility = View.VISIBLE
            binding.nightAnimation.visibility = View.GONE
        }

        binding.twoMinBtn.setOnClickListener {
            startTimer(2 * 60 * 1000)
            hideButtons()
        }

        binding.fiveMinBtn.setOnClickListener {
            startTimer(5 * 60 * 1000)
            hideButtons()
        }

        binding.tenMinBtn.setOnClickListener {
            startTimer(10 * 60 * 1000)
            hideButtons()
        }
    }

    private fun startTimer(duration: Long) {
        binding.timerTextView.visibility = View.VISIBLE
        playMeditationMusic()

        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.timerTextView.text = "00:00"
                stopMeditationMusic()
                showButtons()
            }
        }.start()
    }

    private fun hideButtons() {
        binding.buttonsLayout.visibility = View.GONE
    }

    private fun showButtons() {
        binding.buttonsLayout.visibility = View.VISIBLE
        binding.timerTextView.visibility = View.GONE
    }

    private fun playMeditationMusic() {
        // Initialize MediaPlayer with a meditation music resource
        mediaPlayer = MediaPlayer.create(this, R.raw.meditation_music)
        mediaPlayer?.isLooping = true // Loop music for continuous playback
        mediaPlayer?.start()
    }

    private fun stopMeditationMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }


}
