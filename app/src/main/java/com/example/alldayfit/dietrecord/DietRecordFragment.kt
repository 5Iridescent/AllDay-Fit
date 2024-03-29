package com.example.alldayfit.dietrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.alldayfit.R
import com.example.alldayfit.databinding.DietRecordFragmentBinding
import com.example.alldayfit.databinding.DietRecordMealItemBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DietRecordFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }
    private var _binding: DietRecordFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var dietRecordChart: BarChart
    private val viewModel: DietRecordViewModel by lazy {
        ViewModelProvider(
            this,
            DietRecordViewModelFactory()
        )[DietRecordViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DietRecordFragmentBinding.inflate(inflater, container, false)
        initView()

        dietRecordChart = binding.dietRecordChart

        val carbDataSet = BarDataSet(getBarEntries("탄수화물"), "탄수화물")
        val proteinDataSet = BarDataSet(getBarEntries("단백질"), "단백질")
        val fatDataSet = BarDataSet(getBarEntries("지방"), "지방")
        val caloriesDataSet = BarDataSet(getBarEntries("칼로리"), "칼로리")

        carbDataSet.color = ContextCompat.getColor(requireContext(), R.color.green)
        proteinDataSet.color = ContextCompat.getColor(requireContext(), R.color.yellow)
        fatDataSet.color = ContextCompat.getColor(requireContext(), R.color.black)
        caloriesDataSet.color = ContextCompat.getColor(requireContext(), R.color.dark_blue)

        val data = BarData(carbDataSet, proteinDataSet, fatDataSet, caloriesDataSet)
        dietRecordChart.data = data

        data.barWidth = 0.15f

        dietRecordChart.description.isEnabled = false
        dietRecordChart.setDrawGridBackground(false)

        val xAxis = dietRecordChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("탄수화물", "단백질", "지방", "칼로리"))
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        dietRecordChart.groupBars(0f, 0.05f, 0.05f)

        dietRecordChart.invalidate()

        sharedViewModel.selectedImageUri.observe(viewLifecycleOwner, { uri ->
            if (uri != null) {
                Glide.with(requireContext())
                    .load(uri)
                    .centerCrop()
                    .into(binding.breakfastView.addMealView)
            } else {
                val dietIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_diet)
                binding.breakfastView.addMealView.setImageDrawable(dietIcon)
            }
        })


        return binding.root
    }

    private fun getBarEntries(s: String): List<BarEntry> {
        val entries = ArrayList<BarEntry>()
        val random = java.util.Random()

        val carbs = random.nextFloat() * 100 // 탄수화물
        val protein = random.nextFloat() * 100 // 단백질
        val fat = random.nextFloat() * 100 // 지방
        val calories = (carbs * 4 + protein * 4 + fat * 9).toFloat() // 칼로리 계산

        entries.add(BarEntry(0f, carbs))
        entries.add(BarEntry(1f, protein))
        entries.add(BarEntry(2f, fat))
        entries.add(BarEntry(3f, calories))

        return entries
    }

    private fun initView() = with(binding) {
        breakfastView.mealTxt.text = getString(R.string.diet_record_breakfast)
        lunchView.mealTxt.text = getString(R.string.diet_record_lunch)
        dinnerView.mealTxt.text = getString(R.string.diet_record_dinner)
        snackView.mealTxt.text = getString(R.string.diet_record_snack)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        breakfastView.showDietAddDialog()
        lunchView.showDietAddDialog()
        dinnerView.showDietAddDialog()
        snackView.showDietAddDialog()
    }

    private fun DietRecordMealItemBinding.showDietAddDialog() {
        this.addMealView.setOnClickListener {
            findNavController().navigate(
                DietRecordFragmentDirections.actionDietRecordFragmentToDietRecordAddDialog(
                    this.mealTxt.text.toString()
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}