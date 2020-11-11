package start.android.kotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import start.android.kotlin.data.MyPic
import start.android.kotlin.utils.InjectUtils

class MainViewModel(): ViewModel() {
	private val picList = mutableListOf<MyPic>()
	private val pics = MutableLiveData<List<MyPic>>()

	init {
		Observable.fromCallable<List<String>> {
			return@fromCallable InjectUtils.loadAllPic()
		}
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe { result ->
			for(e in result) {
				picList.add(MyPic(e))
				pics.value = picList
			}
		}
	}

	fun addPic(pic: MyPic) {
		picList.add(pic)
		pics.value = picList
	}

	fun remove(position:Int) {
		picList.removeAt(position)
		pics.value = picList
	}

	fun getPics() = pics as LiveData<List<MyPic>>
}
