package com.aeryz.seimbanginapp.ui.transaction.createTransaction

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.data.local.datasource.TransactionCategoryDataSource
import com.aeryz.seimbanginapp.databinding.ActivityCreateTransactionBinding
import com.aeryz.seimbanginapp.ui.transaction.transactionHistory.TransactionHistoryActivity
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTransactionBinding

    private val viewModel: CreateTransactionViewModel by viewModel()

    private var selectedCategory: String = ""

    private var selectedType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar()
        getTransactionCategory()
        getTransactionType()
        setOnClickListener()
        observeCreateTransactionResult()
    }

    private fun observeCreateTransactionResult() {
        viewModel.createTransactionResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnCreateTransaction.isVisible = true
                    showToast(getString(R.string.text_create_transaction_success), FancyToast.SUCCESS)
                    navigateToTransactionHistory()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnCreateTransaction.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnCreateTransaction.isVisible = true
                    if (it.exception is ApiException) {
                        showToast(it.exception.getParsedError()?.message, FancyToast.ERROR)
                    }
                }
            )
        }
    }

    private fun navigateToTransactionHistory() {
        val intent = Intent(this, TransactionHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun setOnClickListener() {
        binding.btnCreateTransaction.setOnClickListener {
            createTransaction()
        }
    }

    private fun createTransaction() {
        val amountStr = binding.etAmount.text.toString().trim()
        val descriptionStr = binding.etDescription.text.toString().trim()
        if (isFormValid()) {
            val type = selectedType
            val category = selectedCategory
            val amount = amountStr.toDouble()
            viewModel.createTransaction(type, category, amount, descriptionStr)
        }
    }

    private fun isFormValid(): Boolean {
        val amountStr = binding.etAmount.text.toString().trim()
        val descriptionStr = binding.etDescription.text.toString().trim()
        return checkAmountValidation(amountStr)
                && checkDescriptionValidation(descriptionStr)
    }

    private fun getTransactionType() {
        binding.rgTransactionType.setOnCheckedChangeListener { radioGroup, checkedId ->
            val selectedRadioButton = binding.root.findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton.text.toString()
            if (selectedText == getString(R.string.text_income)) selectedType = 0 else selectedType = 1
        }
    }

    private fun getTransactionCategory() {
        val categories = TransactionCategoryDataSource(this).getCategories()
        val adapter = CategorySpinnerAdapter(this, categories)
        binding.spinnerCategory.adapter = adapter
        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedCategory = categories[position].value
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(parent.context,
                        getString(R.string.text_please_select_category), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun setupToolBar() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun checkAmountValidation(amount: String): Boolean {
        return if (amount.isEmpty()) {
            binding.tilAmount.isErrorEnabled = true
            binding.tilAmount.error = getString(R.string.text_amount_still_empty)
            false
        } else {
            try {
                amount.toDouble()
                binding.tilAmount.isErrorEnabled = false
                true
            } catch (e: NumberFormatException) {
                binding.tilAmount.isErrorEnabled = true
                binding.tilAmount.error = getString(R.string.text_input_valid_number)
                false
            }
        }
    }

    private fun checkDescriptionValidation(description: String): Boolean {
        return if (description.isEmpty()) {
            binding.tilDescription.isErrorEnabled = true
            binding.tilDescription.error = getString(R.string.text_description_still_empty)
            false
        } else {
            binding.tilDescription.isErrorEnabled = false
            true
        }
    }

    private fun showToast(message: String?, type: Int) {
        FancyToast.makeText(
            this,
            message,
            FancyToast.LENGTH_LONG,
            type,
            false
        ).show()
    }
}