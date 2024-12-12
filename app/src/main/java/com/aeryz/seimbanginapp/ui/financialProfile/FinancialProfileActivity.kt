package com.aeryz.seimbanginapp.ui.financialProfile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.data.network.model.profile.FinanceProfile
import com.aeryz.seimbanginapp.databinding.ActivityFinancialProfileBinding
import com.aeryz.seimbanginapp.utils.customPopupDialog
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FinancialProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinancialProfileBinding

    private var selectedRiskType: String = ""

    private val viewModel: FinancialProfileViewModel by viewModel()

    private var isEditMode = false

    private var editMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinancialProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getProfileData()
        observeProfileData()
        setOnClickListener()
        observeUpdateResult()
        setupToolBar()
        updateEditMode()
        setupRiskType()
    }

    private fun setupToolBar() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.financial_profile_menu, menu)
        editMenuItem = menu?.findItem(R.id.action_edit)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                isEditMode = !isEditMode
                updateEditMode()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRiskType() {
        selectedRiskType = "LOW"
        val riskTypes = resources.getStringArray(R.array.risk_management_types)
        val adapter = ArrayAdapter(this, R.layout.layout_item_risk_managemnt, riskTypes)
        (binding.actvRiskType as? AutoCompleteTextView)?.apply {
            setAdapter(adapter)
            setText(selectedRiskType.uppercase(), false)
            setOnItemClickListener { parent, view, position, id ->
                selectedRiskType = riskTypes[position].toString().lowercase()
            }
        }
    }

    private fun updateEditMode() {
        if (isEditMode) {
            editMenuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_edit_off_24)
            binding.etMonthlyIncome.isEnabled = true
            binding.etCurrentSavings.isEnabled = true
            binding.etDebt.isEnabled = true
            binding.etFinancialGoals.isEnabled = true
            binding.actvRiskType.isEnabled = true
            binding.btnUpdate.isEnabled = true
        } else {
            editMenuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_edit_on_24)
            binding.etMonthlyIncome.isEnabled = false
            binding.etCurrentSavings.isEnabled = false
            binding.etDebt.isEnabled = false
            binding.etFinancialGoals.isEnabled = false
            binding.actvRiskType.isEnabled = false
            binding.btnUpdate.isEnabled = false
        }
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.contentState.root.isVisible = false
                    binding.llForm.isVisible = true
                    it.payload?.let { response ->
                        setupForm(response.profileData?.financeProfile)
                    }
                },
                doOnLoading = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = true
                    binding.contentState.tvError.isVisible = false
                    binding.llForm.isVisible = false
                },
                doOnError = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = false
                    binding.contentState.tvError.isVisible = true
                    binding.llForm.isVisible = false
                    if (it.exception is ApiException) {
                        binding.contentState.tvError.text = it.exception.getParsedError()?.message
                    }
                }
            )
        }
    }

    private fun getProfileData() {
        viewModel.getProfileData()
    }

    private fun setupForm(extraFinancialProfile: FinanceProfile?) {
        extraFinancialProfile?.let {
            binding.etMonthlyIncome.setText(extraFinancialProfile.monthlyIncome)
            binding.etCurrentSavings.setText(extraFinancialProfile.currentSavings)
            binding.etDebt.setText(extraFinancialProfile.debt)
            binding.etFinancialGoals.setText(extraFinancialProfile.financialGoals)
            binding.etMonthlyIncome.setText(extraFinancialProfile.monthlyIncome)
            selectedRiskType = extraFinancialProfile.riskManagement.toString()
            val riskTypes = resources.getStringArray(R.array.risk_management_types)
            val adapter = ArrayAdapter(this, R.layout.layout_item_risk_managemnt, riskTypes)
            (binding.actvRiskType as? AutoCompleteTextView)?.apply {
                setAdapter(adapter)
                setText(selectedRiskType.uppercase(), false)
                setOnItemClickListener { parent, view, position, id ->
                    selectedRiskType = riskTypes[position].toString().lowercase()
                }
            }
        }
    }

    private fun observeUpdateResult() {
        viewModel.updateResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnUpdate.isVisible = true
                    customPopupDialog(
                        context = this,
                        type = 1,
                        successMessage = getString(R.string.text_update_financial_profile_success),
                        errorMessage = null
                    ) {
                        getProfileData()
                        isEditMode = false
                        updateEditMode()
                    }
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnUpdate.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnUpdate.isVisible = true
                    if (it.exception is ApiException) {
                        customPopupDialog(
                            context = this,
                            type = 0,
                            successMessage = null,
                            errorMessage = it.exception.getParsedError()?.message
                        )
                    }
                }
            )
        }
    }

    private fun setOnClickListener() {
        binding.btnUpdate.setOnClickListener {
            updateFinancialProfile()
        }
    }

    private fun updateFinancialProfile() {
        val monthlyIncomeStr = binding.etMonthlyIncome.text.toString().trim()
        val currentSavingsStr = binding.etCurrentSavings.text.toString().trim()
        val debtStr = binding.etDebt.text.toString().trim()
        val financialGoalsStr = binding.etFinancialGoals.text.toString().trim()
        val riskType = selectedRiskType
        if (isFormValid()) {
            val monthlyIncome = monthlyIncomeStr.toDouble()
            val currentSavings = currentSavingsStr.toDouble()
            val debt = debtStr.toDouble()
            viewModel.updateFinancialProfile(
                monthlyIncome,
                currentSavings,
                debt,
                financialGoalsStr,
                riskType
            )
        }
    }

    private fun isFormValid(): Boolean {
        val monthlyIncome = binding.etMonthlyIncome.text.toString().trim()
        val currentSavings = binding.etCurrentSavings.text.toString().trim()
        val debt = binding.etDebt.text.toString().trim()
        val financialGoals = binding.etFinancialGoals.text.toString().trim()
        return checkMonthlyIncomeValidation(monthlyIncome) &&
            checkCurrentSavingsValidation(currentSavings) &&
            checkDebtValidation(debt) &&
            checkFinancialGoalsValidation(financialGoals)
    }

    private fun checkMonthlyIncomeValidation(monthlyIncome: String): Boolean {
        return if (monthlyIncome.isEmpty()) {
            binding.tilMonthlyIncome.isErrorEnabled = true
            binding.tilMonthlyIncome.error = getString(R.string.text_monthly_income_still_empty)
            false
        } else {
            try {
                monthlyIncome.toDouble()
                binding.tilMonthlyIncome.isErrorEnabled = false
                true
            } catch (e: NumberFormatException) {
                binding.tilMonthlyIncome.isErrorEnabled = true
                binding.tilMonthlyIncome.error = getString(R.string.text_input_valid_number)
                false
            }
        }
    }

    private fun checkCurrentSavingsValidation(currentSavings: String): Boolean {
        return if (currentSavings.isEmpty()) {
            binding.tilCurrentSavings.isErrorEnabled = true
            binding.tilCurrentSavings.error = getString(R.string.text_current_savings_still_empty)
            false
        } else {
            try {
                currentSavings.toDouble()
                binding.tilCurrentSavings.isErrorEnabled = false
                true
            } catch (e: NumberFormatException) {
                binding.tilCurrentSavings.isErrorEnabled = true
                binding.tilCurrentSavings.error = getString(R.string.text_input_valid_number)
                false
            }
        }
    }

    private fun checkDebtValidation(debt: String): Boolean {
        return if (debt.isEmpty()) {
            binding.tilDebt.isErrorEnabled = true
            binding.tilDebt.error = getString(R.string.text_debt_still_empty)
            false
        } else {
            try {
                debt.toDouble()
                binding.tilDebt.isErrorEnabled = false
                true
            } catch (e: NumberFormatException) {
                binding.tilDebt.isErrorEnabled = true
                binding.tilDebt.error = getString(R.string.text_input_valid_number)
                false
            }
        }
    }

    private fun checkFinancialGoalsValidation(financialGoals: String): Boolean {
        return if (financialGoals.isEmpty()) {
            binding.tilFinancialGoals.isErrorEnabled = true
            binding.tilFinancialGoals.error = getString(R.string.text_financial_goals_still_empty)
            false
        } else {
            binding.tilFinancialGoals.isErrorEnabled = false
            true
        }
    }
}
