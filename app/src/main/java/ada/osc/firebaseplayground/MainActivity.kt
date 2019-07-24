package ada.osc.firebaseplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()

   private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel <MainViewModel>()

        viewModel.getStatisticByDays()

        parkBtn.setOnClickListener {
            viewModel.firstPicked("park")
        }
        castleBtn.setOnClickListener {
            viewModel.firstPicked("castle")
        }
        otherContentBtn.setOnClickListener {
            viewModel.firstPicked("other content")
        }

        recommendationBtn.setOnClickListener {
            viewModel.secondPicked("recommendation")
        }
        onlineMediaBtn.setOnClickListener {
            viewModel.secondPicked("online media")
        }
        otherMediaBtn.setOnClickListener {
            viewModel.secondPicked("other media")
        }

        satisfiedBtn.setOnClickListener {
            viewModel.thirdPicked("happy")
        }
        dissatisfiedBtn.setOnClickListener {
            viewModel.thirdPicked("sad")
        }

        reset.setOnClickListener {
            viewModel.resetAnswers()
        }

        viewModel.answerData.observe(this, Observer { data ->
        data.let {
            if (data.firstAnswer != "" && data.secondAnswer != "" && data.thirdAnswer != ""){
                when(data.firstAnswer){
                    "park" -> viewModel.updateData("park", "question_one")
                    "castle" -> viewModel.updateData("castle", "question_one")
                    "other content" -> viewModel.updateData("extra_content", "question_one")
                }

                when(data.secondAnswer){
                    "recommendation" -> viewModel.updateData("recommendation","question_two")
                    "online media" -> viewModel.updateData("online_media", "question_two")
                    "other media" ->viewModel.updateData("other_media", "question_two")
                }

                when(data.thirdAnswer){
                    "happy" -> viewModel.updateData("satisfied","third_question")
                    "sad" -> viewModel.updateData("dissatisfied","third_question")
                }
            }
        }
        })
    }



}
