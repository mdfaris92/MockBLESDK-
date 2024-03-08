package com.royalenfield.mocksdk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@InternalCoroutinesApi
object MockSDK {

    private val speed = CoroutineScope(Dispatchers.Default)
    private val discharging = CoroutineScope(Dispatchers.Default)

    interface FlowCallback {
        fun onResult(result: Int)
    }


    fun startSpeed(flowCallback: FlowCallback, freq: Long) {
        speed.launch {
            flow {
                while (true) {
                    (0..160).forEach {
                        delay(freq)
                        emit(it)

                    }
                }
            }.flowOn(Dispatchers.Default)
                .collect {
                    flowCallback.onResult(it)
                }
        }
    }

    fun startDischarging(flowCallback: FlowCallback, freq: Long) {
        discharging.launch {
            flow {
                while (true) {
                    (100 downTo 0).forEach {
                        delay(freq)
                        emit(it)

                    }
                }
            }.flowOn(Dispatchers.Default)

                .collect { flowCallback.onResult(it) }
        }
    }




    fun stopSpeed() {
        speed.cancel()
    }

    fun stopDischarging() {
        discharging.cancel()
    }
}
