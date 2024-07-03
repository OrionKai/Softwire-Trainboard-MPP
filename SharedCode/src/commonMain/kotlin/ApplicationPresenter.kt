package com.jetbrains.handson.mpp.mobile

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.URLProtocol
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ApplicationPresenter: ApplicationContract.Presenter() {

    private val dispatchers = AppDispatchersImpl()
    private var view: ApplicationContract.View? = null
    private val job: Job = SupervisorJob()
    private val fetcher: LiveTrainTimeFetcher = LiveTrainTimeFetcher()

    override val coroutineContext: CoroutineContext
        get() = dispatchers.main + job

    override fun onViewTaken(view: ApplicationContract.View) {
        this.view = view
    }

    override fun getFetcher(): LiveTrainTimeFetcher {
        return fetcher
    }

    override fun fetchLiveTrainTimes() {
        val client = HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

        launch(coroutineContext) {
            //val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val currentDateTimeString = "2024-07-03T12:16:27.371Z"
            val  response: HttpResponse = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "mobile-api-softwire2.lner.co.uk"
                    path("v1", "fares")
                    parameters.append("originStation", "KGX")
                    parameters.append("destinationStation", "EDB")
                    parameters.append("outboundDateTime", currentDateTimeString)
                    parameters.append("numberOfChildren", "2")
                    parameters.append("numberOfAdults", "2")
                }
                header("x-api-key", "amogus")
            }
            view?.setLiveTrainTimesInfo(response.readText())
            //println(response.readText())
        }

    }
}
