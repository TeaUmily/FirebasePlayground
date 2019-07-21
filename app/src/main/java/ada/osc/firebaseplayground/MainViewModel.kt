package ada.osc.firebaseplayground

import ada.osc.firebaseplayground.AnswerModel.Answer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel(){

    val answerData = MutableLiveData<Answer>()

    lateinit var answer: Answer

    var firstAnswer = ""
    var secondAnswer = ""
    var thirdAnswer = ""

    fun updateAnswer() {
        val answer = Answer(firstAnswer,secondAnswer,thirdAnswer)
        answerData.value = answer
    }


    public fun firstPicked(answer: String){
        firstAnswer = answer
        updateAnswer()
    }

    public fun secondPicked(answer: String){
        secondAnswer = answer
        updateAnswer()
    }

    public fun thirdPicked(answer: String){
        thirdAnswer = answer
        updateAnswer()
    }

    fun resetAnswers() {
         firstAnswer = ""
         secondAnswer = ""
         thirdAnswer = ""

        updateAnswer()
    }
}