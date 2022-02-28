package ui.smartpro.firestorepuls

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ui.smartpro.firestorepuls.databinding.ActivityMainBinding
import ui.smartpro.firestorepuls.ui.PulsViewModel
import ui.smartpro.firestorepuls.ui.adapter.PulsAdapter
import ui.smartpro.firestorepuls.utils.*

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<PulsViewModel>()
    lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PulsAdapter
    private val prefs by inject<SharedPreferencesHelper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomSheetBehavior(binding.bottomSheet.bottomSheetContainer)
        initialAdapter()
        binding.bottomSheet.addItem.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            setData()
            getData()
        }

        binding.fab.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.bottomSheet.timeDay.text = asTimeSchedule()
        }
        getData()
        initialBD()
    }

    private fun initialAdapter() {
        recyclerView = binding.healfRv
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.setHasFixedSize(true)
        adapter = PulsAdapter()
        recyclerView.adapter = adapter
    }

    private fun setData() {
        val id = getId()
        binding.bottomSheet.timeDay.text = asTimeSchedule()
        val time = binding.bottomSheet.timeDay.text
        val pressureOne = binding.bottomSheet.pressureOne.text?.toString()?.trim()
        val pressureTwo = binding.bottomSheet.pressureTwo.text?.toString()?.trim()
        val puls = binding.bottomSheet.pulse.text?.toString()?.trim()
        val date = asDate()
        if (time.isNullOrEmpty() || pressureOne.isNullOrEmpty() || pressureTwo.isNullOrEmpty() || puls.isNullOrEmpty()) {
            showToast("Какое-то поле не заполнено")
        }
        if (time?.isNotEmpty() == true && pressureOne?.isNotEmpty() == true && pressureTwo?.isNotEmpty() == true && puls?.isNotEmpty() == true) {
            adapter.clearPuls()
            vm.savePulsToDb(id, date, time.toString(), pressureOne, pressureTwo, puls)
        }
    }

    private fun getData() {
        vm.getAllItems(adapter)
        lifecycleScope.launchWhenResumed {
            vm.pulsFlow.collect { data ->
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.constraintContainer.alpha = 1.0f
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.constraintContainer.alpha = 1.0f
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun initialBD() {
        if (prefs.isFirstOpen || prefs.currentDate != asDate()) {
            vm.createNode(asDate())
            prefs.isFirstOpen = false
            prefs.currentDate = asDate()
        }
    }

    private fun initialSampleBD() {
        vm.createNode("2022.02.26")
    }

    private fun customBackground() {

    val gradientDrawable = GradientDrawable(
    GradientDrawable.Orientation.TOP_BOTTOM,
    intArrayOf(
        Color.parseColor("#008000"),
    Color.parseColor("#ADFF2F"))
    );
    gradientDrawable.cornerRadius = 0f;

    //Set Gradient
//    linearLayout.setBackground(gradientDrawable);
    }

}