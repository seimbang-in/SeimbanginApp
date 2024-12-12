package com.aeryz.seimbanginapp.ui.transaction.createTransaction

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.data.network.model.createTransaction.TransactionItemRequest
import com.aeryz.seimbanginapp.data.network.model.ocr.OcrData
import com.aeryz.seimbanginapp.databinding.ActivityCreateTransactionBinding
import com.aeryz.seimbanginapp.ui.transaction.transactionHistory.TransactionHistoryActivity
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import com.aeryz.seimbanginapp.utils.withCurrencyFormat
import com.airbnb.lottie.LottieAnimationView
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTransactionActivity : AppCompatActivity(), OnTransactionItemChangeListener {

    private lateinit var binding: ActivityCreateTransactionBinding

    private val viewModel: CreateTransactionViewModel by viewModel()

    private var selectedType: Int = 1

    private val transactionItems = mutableListOf(TransactionItemRequest())

    private lateinit var transactionItemAdapter: TransactionItemAdapter

    private var soundPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.getParcelableExtra<OcrData>(EXTRA_OCR)
        if (extras != null) {
            bindData(extras)
        }
        setupToolBar()
        getTransactionType()
        setOnClickListener()
        observeCreateTransactionResult()
        setTransactionItemRv()
        updateTotalPrice()
    }

    private fun bindData(ocrData: OcrData?) {
        val products = ocrData?.items
        transactionItems.clear()
        products?.map { product ->
            val transactionItem = TransactionItemRequest(
                itemName = product.itemName,
                price = product.price ?: 1,
                quantity = product.quantity ?: 1,
                category = product.category
            )
            transactionItems.add(transactionItem)
        }
    }

    private fun observeCreateTransactionResult() {
        viewModel.createTransactionResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnCreateTransaction.isVisible = true
                    customPopup(1, null)
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnCreateTransaction.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnCreateTransaction.isVisible = true
                    if (it.exception is ApiException) {
                        customPopup(0, it.exception.getParsedError()?.message)
                    }
                }
            )
        }
    }

    private fun navigateToTransactionHistory() {
        val intent = Intent(this, TransactionHistoryActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        finish()
    }

    private fun setOnClickListener() {
        binding.btnCreateTransaction.setOnClickListener {
            Log.d("ITEMS", transactionItems.toString())
            createTransaction()
        }
    }

    private fun setTransactionItemRv() {
        transactionItemAdapter = TransactionItemAdapter(transactionItems, this)
        binding.rvTransactionItem.adapter = transactionItemAdapter
        binding.rvTransactionItem.layoutManager = LinearLayoutManager(this)
        binding.fabAddItem.setOnClickListener {
            transactionItems.add(TransactionItemRequest())
            transactionItemAdapter.notifyItemInserted(transactionItems.size - 1)
            updateTotalPrice()
        }
    }

    private fun createTransaction() {
        val transactionName = binding.etTransactionName.text.toString().trim()
        if (isFormValid()) {
            val type = selectedType
            if (transactionItemAdapter.validateItems()) {
                viewModel.createTransaction(type, transactionName, transactionItems)
            } else {
                showToast("Please correct the errors in the form", FancyToast.ERROR)
            }
        }
    }

    private fun isFormValid(): Boolean {
        val transactionName = binding.etTransactionName.text.toString().trim()
        return checkTransactionNameValidation(transactionName)
    }

    private fun getTransactionType() {
        binding.rgTransactionType.setOnCheckedChangeListener { radioGroup, checkedId ->
            val selectedRadioButton = binding.root.findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton.text.toString()
            if (selectedText == getString(R.string.text_income)) {
                selectedType =
                    0
            } else {
                selectedType = 1
            }
        }
    }

    private fun setupToolBar() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun checkTransactionNameValidation(name: String): Boolean {
        return if (name.isEmpty()) {
            binding.tilTransactionName.isErrorEnabled = true
            binding.tilTransactionName.error = getString(R.string.text_description_still_empty)
            false
        } else {
            binding.tilTransactionName.isErrorEnabled = false
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

    override fun onTransactionItemChanged(transactionItems: List<TransactionItemRequest>) {
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val totalPrice = transactionItems.sumOf { it.price * it.quantity }
        binding.totalPrice.text = withCurrencyFormat(totalPrice.toString())
    }

    private fun customPopup(type: Int, message: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_layout_popup)
        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams
        val animationView = dialog.findViewById<LottieAnimationView>(R.id.animation_view)
        val title = dialog.findViewById<TextView>(R.id.tv_title)
        val description = dialog.findViewById<TextView>(R.id.tv_description)
        val button = dialog.findViewById<Button>(R.id.btn_continue)
        if (type == 1) {
            soundPlayer = MediaPlayer.create(this, R.raw.sound_success)
            soundPlayer?.start()
            animationView.setAnimation(R.raw.animation_success)
            title.text = getString(R.string.text_success)
            description.text = getString(R.string.text_create_transaction_success)
            button.setOnClickListener {
                dialog.dismiss()
                navigateToTransactionHistory()
            }
        } else {
            soundPlayer = MediaPlayer.create(this, R.raw.sound_error)
            soundPlayer?.start()
            animationView.setAnimation(R.raw.animation_error)
            title.text = getString(R.string.text_failed)
            title.setTextColor(ContextCompat.getColor(this, R.color.error_500))
            description.text = getString(R.string.text_failed_creating_transaction, message)
            button.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.error_500)
            button.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
        dialog.setOnDismissListener {
            soundPlayer?.release()
            soundPlayer = null
        }
    }

    companion object {
        const val EXTRA_OCR = "OCR_DATA"

        fun startActivity(context: Context, ocrData: OcrData) {
            val intent = Intent(context, CreateTransactionActivity::class.java)
            intent.putExtra(EXTRA_OCR, ocrData)
            context.startActivity(intent)
        }
    }
}
