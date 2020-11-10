package start.android.kotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import start.android.kotlin.data.MyPic
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable

class MainViewModel(): ViewModel() {
	private val picList = mutableListOf<MyPic>()
	private val pics = MutableLiveData<List<MyPic>>()

	init {
		Observable.fromCallable<List<String>> {
			return@fromCallable loadAllPic()
		}
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe { result ->
			for(e in result) {
				println("url::::$e")
				picList.add(MyPic(e))
				pics.value = picList
			}
		}
	}

	fun addPic(pic: MyPic) {
		picList.add(pic)
		pics.value = picList
	}

	fun getPics() = pics as LiveData<List<MyPic>>
}

fun loadAllPic() :List<String> {
	val url = "https://www.google.com/search?q=java+sparrow&tbm=isch&ved=2ahUKEwjnsoje2vfsAhUNWpQKHd0TANMQ2-cCegQIABAA&oq=java+sparrow&gs_lcp=CgNpbWcQAzIECCMQJzIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjoFCAAQsQM6AggAUN8iWMU3YIE5aABwAHgAgAGWAYgBtQySAQQwLjEymAEAoAEBqgELZ3dzLXdpei1pbWfAAQE&sclient=img&ei=b2OqX6e_Fo200QTdp4CYDQ&bih=675&biw=1280&client=firefox-b-d"
	val document = Jsoup.connect(url).get()
	val result = mutableListOf<String>()
	val media = document.select("img")
	for(e in media) {
		result.add(e.attr("data-src"))
	}
	return result.filterNotNull().filter { e -> e.length > 0 }
}
