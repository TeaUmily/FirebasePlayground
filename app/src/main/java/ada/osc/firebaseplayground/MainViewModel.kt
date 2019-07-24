package ada.osc.firebaseplayground

import ada.osc.firebaseplayground.AnswerModel.Answer
import ada.osc.firebaseplayground.interactor.Interactor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainViewModel(private val interactor: Interactor) : ViewModel() {

    val answerData = MutableLiveData<Answer>()
    val castleData = MutableLiveData<Int>()
    lateinit var answer: Answer


    var firstAnswer = ""
    var secondAnswer = ""
    var thirdAnswer = ""

    init {
        castleData.value = 0
    }

    fun updateAnswer() {
        val answer = Answer(firstAnswer, secondAnswer, thirdAnswer)
        answerData.value = answer
    }


    public fun firstPicked(answer: String) {
        firstAnswer = answer
        updateAnswer()
    }

    public fun secondPicked(answer: String) {
        secondAnswer = answer
        updateAnswer()
    }

    public fun thirdPicked(answer: String) {
        thirdAnswer = answer
        updateAnswer()
    }

    fun resetAnswers() {
        firstAnswer = ""
        secondAnswer = ""
        thirdAnswer = ""

        updateAnswer()
    }


    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun <T> Observable<T>.ptSubscribe() {
        addDisposable(this.subscribe({}, { it.printStackTrace() }))
    }

    private fun addDisposable(disposable: Disposable) {
        if (compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(disposable)
    }


    public fun updateData(answer: String, question: String) {
        val day = 2
        val month = 2
        val year = 2019

        interactor.getDayData(year, month, day, question, answer).doOnError {
            if (it.message == "Null is not a valid element") {
                interactor.writeDayData(year, month, day, question, answer, 1).ptSubscribe()
            }
        }.doOnNext {
            interactor.writeDayData(year, month, day, question, answer, it + 1).ptSubscribe()
        }.ptSubscribe()

        interactor.getMonthData(year, month, question, answer).doOnError {
            if (it.message == "Null is not a valid element") {
                interactor.writeMonthData(year, month, question, answer, 1).ptSubscribe()
            }
        }.doOnNext {
            interactor.writeMonthData(year, month, question, answer, it + 1).ptSubscribe()
        }.ptSubscribe()

        interactor.getYearData(year, question, answer).doOnError {
            if (it.message == "Null is not a valid element") {
                interactor.writeYearData(year, question, answer, 1).ptSubscribe()
            }
        }.doOnNext {
            interactor.writeYearData(year, question, answer, it + 1).ptSubscribe()
        }.ptSubscribe()

    }

    public fun getStatisticByDays(){
        interactor.getVisitStatisticsByDays(2019, 2).doOnNext {
            val a = it
        }.ptSubscribe()

        interactor.getVisitStatisticsByMonths(2019).doOnNext {
            val b = it
        }.ptSubscribe()

        interactor.getVisitStatisticsByYears().doOnNext {
            val c = it
        }.ptSubscribe()


    }

}