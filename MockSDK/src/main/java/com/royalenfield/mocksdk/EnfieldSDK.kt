package com.royalenfield.mocksdk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.EnumMap

object EnfieldSDK {


    private val speed = CoroutineScope(Dispatchers.Default)
    private val discharging = CoroutineScope(Dispatchers.Default)
    private val charging = CoroutineScope(Dispatchers.Default)
    private val rightInd = CoroutineScope(Dispatchers.Default)
    private val leftInd = CoroutineScope(Dispatchers.Default)
    private val rideMode = CoroutineScope(Dispatchers.Default)
    private val odo = CoroutineScope(Dispatchers.Default)
    private val highBeam = CoroutineScope(Dispatchers.Default)
    private val lowBeam = CoroutineScope(Dispatchers.Default)
    private val hazard = CoroutineScope(Dispatchers.Default)
    private val stand = CoroutineScope(Dispatchers.Default)


    private var isCharging : Boolean = false
    private var isLeftInd : Boolean = false
    private var isRightInd : Boolean = false
    private var isHighB : Boolean = false
    private var isLowB : Boolean = false
    private var isHazard : Boolean = false
    private var isSideStand : Boolean = false
    private var modeData : MODE = MODE.N


    enum class KEY {
        SPEED,
        SOC,
        CHARGE,
        RIGHT_IND,
        LEFT_IND,
        RIDE_MODE,
        ODO,
        HIGH_B,
        LOW_B,
        HAZARD,
        STAND
    }

    enum class MODE{
        N,
        D,
        R,
        P
    }

    enum class DRIVE_MODE{
        ECO,
        TOUR,
        SPORT
    }

    private  val maps: MutableMap<KEY, String> = EnumMap(KEY::class.java)

    interface enfieldCallback {
        fun onResult(result: MutableMap<KEY, String>)
    }

    fun enableLeftInd(flag : Boolean){
        isLeftInd = flag
    }
    fun enableRightInd(flag : Boolean){
        isRightInd = flag
    }
    fun enableHighB(flag : Boolean){
        isHighB = flag
    }
    fun enableLowB(flag : Boolean){
        isLowB = flag
    }
    fun enableHazard(flag : Boolean){
        isHazard = flag
    }
    fun enableSideStand(flag: Boolean){
        isSideStand = flag
    }
    fun enableCharging(flag: Boolean){
        isCharging = flag
    }
    fun setMode(data : MODE){
        modeData = data
    }

    fun getBleObject(callback : enfieldCallback){


        speed.launch {
            flow {
                while (true) {
                    (0..160).forEach {
                        delay(500)
                        emit(it)
                    }
                }
            }.flowOn(Dispatchers.Default)
                .collect {
                    maps[KEY.SPEED] = it.toString()
                    callback.onResult(maps)
                }
        }

        discharging.launch {
            flow {
                while (true) {
                    (100 downTo 0).forEach {
                        delay(5000)
                        emit(it)
                    }
                }
            }.flowOn(Dispatchers.Default)
                .collect {
                    maps.put(KEY.SOC,it.toString())
                    callback.onResult(maps)
                }
        }

        odo.launch {
            flow {
                while (true) {
                    (102001 .. 999999).forEach {
                        delay(10000)
                        emit(it)

                    }
                }
            }.flowOn(Dispatchers.Default)
                .collect {
                    maps[KEY.ODO] = it.toString()
                    callback.onResult(maps)
                }
        }

        rideMode.launch {
            flow {
                while (true) {
                    delay(5000)
                    emit(modeData)
                }
            }.flowOn(Dispatchers.Default)
                .collect {
                    maps[KEY.RIDE_MODE] = it.toString()
                    callback.onResult(maps)
                }
        }

        charging.launch {
                    flow {
                        while (true) {
                            (0 .. 100).forEach {
                                if(isCharging){
                                    delay(5000)
                                    emit(it)
                                }else{
                                    delay(5000)
                                    emit(0)
                                }
                            }
                        }
                    }.flowOn(Dispatchers.Default)
                        .collect {
                            maps[KEY.CHARGE] = it.toString()
                            callback.onResult(maps)
                        }
                }

        leftInd.launch {
                    flow {
                        while (true) {
                            if (isLeftInd) {
                                delay(2000)
                                emit(true)
                            } else {
                                delay(2000)
                                emit(false)
                            }

                        }
                    }.flowOn(Dispatchers.Default)
                        .collect {
                            maps[KEY.LEFT_IND] = it.toString()
                            callback.onResult(maps)
                        }
                }

        rightInd.launch {
                    flow {
                        while (true) {
                            if (isRightInd) {
                                delay(2000)
                                emit(true)
                            } else {
                                delay(2000)
                                emit(false)
                            }
                        }
                    }.flowOn(Dispatchers.Default)
                        .collect {
                            maps[KEY.RIGHT_IND] = it.toString()
                            callback.onResult(maps)
                        }
                }

        highBeam.launch {
                    flow {
                        while (true) {
                            if (isHighB) {
                                delay(5000)
                                emit(true)
                            } else {
                                delay(5000)
                                emit(false)
                            }
                        }
                    }.flowOn(Dispatchers.Default)
                        .collect {
                            maps[KEY.HIGH_B] = it.toString()
                            callback.onResult(maps)
                        }
                }

        lowBeam.launch {
                    flow {
                        while (true) {
                            if (isLowB) {
                                delay(5000)
                                emit(true)
                            } else {
                                delay(5000)
                                emit(false)
                            }

                        }
                    }.flowOn(Dispatchers.Default)
                        .collect {
                            maps[KEY.LOW_B] = it.toString()
                            callback.onResult(maps)
                        }
                }

        stand.launch {
                    flow {
                        while (true) {
                            if (isSideStand) {
                                delay(5000)
                                emit(true)
                            } else {
                                delay(5000)
                                emit(false)
                            }

                        }
                    }.flowOn(Dispatchers.Default)
                        .collect {
                            maps[KEY.STAND] = it.toString()
                            callback.onResult(maps)
                        }
                }

        hazard.launch {
                    flow {
                        while (true) {
                            if (isHazard) {
                                delay(5000)
                                emit(true)
                            } else {
                                delay(5000)
                                emit(false)
                            }

                        }
                    }.flowOn(Dispatchers.Default)
                        .collect {
                            maps[KEY.HAZARD] = it.toString()
                            callback.onResult(maps)
                        }
                }

    }

    fun stopEmittingValue() {
        speed.cancel()
        discharging.cancel()
        charging.cancel()
        rightInd.cancel()
        leftInd.cancel()
        rideMode.cancel()
        odo.cancel()
        highBeam.cancel()
        lowBeam.cancel()
        stand.cancel()
        hazard.cancel()

    }

}