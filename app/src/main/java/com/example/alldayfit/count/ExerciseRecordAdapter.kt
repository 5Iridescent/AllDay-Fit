import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.TimeUtils.formatDuration
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.count.ExerciseRecord
import com.example.alldayfit.databinding.CountItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseRecordAdapter(private val records: List<ExerciseRecord>) :
RecyclerView.Adapter<ExerciseRecordAdapter.ViewHolder>() {

    class ViewHolder(private val binding: CountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: ExerciseRecord) {
            binding.exerciseTitele.text = record.timestamp

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CountItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount(): Int {
        return records.size
    }
}






