package com.example.hydro_sheungkit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.hydro_sheungkit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener {
            val morningFromUI: String = binding.etMorning.text.toString()
            val eveningFromUI: String = binding.etEvening.text.toString()

            if (morningFromUI.isNotEmpty() && eveningFromUI.isNotEmpty()){
                val morningCharge: Double = morningFromUI.toDouble() * 0.132
                val eveningCharge: Double = eveningFromUI.toDouble() * 0.094
                val totalUsageCharge: Double = morningCharge + eveningCharge
                val envirRebate: Double = totalUsageCharge * if (binding.isRenewable.isChecked) 0.09 else 0.0
                val subtotal: Double = totalUsageCharge - envirRebate
                val tax: Double = subtotal * 0.13
                val total: Double = subtotal + tax

                binding.breakdown.text = """
                    Morning usage charge: $${String.format("%.2f", morningCharge)}
                    Evening usage charge: $${String.format("%.2f", eveningCharge)}
                    Total Usage charge: $${String.format("%.2f", totalUsageCharge)}
                    Environmental Rebate: $${String.format("%.2f", envirRebate)}
                    Subtotal: $${String.format("%.2f", subtotal)}
                    Tax: $${String.format("%.2f", tax)}
                    """.trimIndent()
                binding.result.text = "YOU MUST PAY: $${String.format("%.2f", total)}"

                binding.title.visibility = View.VISIBLE
                binding.breakdown.visibility = View.VISIBLE
                binding.result.visibility = View.VISIBLE
                binding.error.visibility = View.GONE
            } else {
                binding.error.text = "Error: All fields must be filled in."
                binding.error.visibility = View.VISIBLE
                binding.title.visibility = View.GONE
                binding.breakdown.visibility = View.GONE
                binding.result.visibility = View.GONE
            }
        }

        binding.btnReset.setOnClickListener {
            binding.etMorning.text.clear()
            binding.etEvening.text.clear()
            binding.error.visibility = View.GONE
            binding.title.visibility = View.GONE
            binding.breakdown.visibility = View.GONE
            binding.result.visibility = View.GONE
            binding.isRenewable.isChecked = false
        }
    }
}

