package ada.osc.firebaseplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


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
                    "park" -> increaseValue("park", "question_one")
                    "castle" -> increaseValue("castle", "question_one")
                    "other content" -> increaseValue("extra_content", "question_one")
                }

                when(data.secondAnswer){
                    "recommendation" -> increaseValue("recommendation","question_two")
                    "online media" -> increaseValue("online_media", "question_two")
                    "other media" -> increaseValue("other_media", "question_two")
                }

                when(data.thirdAnswer){
                    "happy" -> increaseValue("satisfied","third_question")
                    "sad" -> increaseValue("dissatisfied","third_question")
                }
            }
        }
        })
    }

    private fun increaseValue(answer: String, question: String) {

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val day = dayInput.text
                val month = monthInput.text
                val year = 2019

                val dataYear = dataSnapshot.child("year/$year/month/$month/$question/$answer").value
                database.getReference("year/$year/$question/$answer").setValue(dataYear.hashCode()+1)

                val dataMonth = dataSnapshot.child("year/$year/month/$month/$question/$answer").value
                database.getReference("year/$year/month/$month/$question/$answer").setValue(dataMonth.hashCode()+1)

                val dataDay = dataSnapshot.child("year/$year/month/$month/day/$day/$question/$answer").value
                database.getReference("year/$year/month/$month/day/$day/$question/$answer").setValue(dataDay.hashCode()+1)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("AAA", "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.reference.addListenerForSingleValueEvent(listener)

    }

}
