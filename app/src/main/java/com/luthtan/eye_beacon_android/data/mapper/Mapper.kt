package com.luthtan.eye_beacon_android.data.mapper

import io.reactivex.functions.Function

abstract class Mapper<FROM, TO> : Function<FROM?, TO?> {
    @Throws(Exception::class)
    fun apply(fromList: Collection<FROM>): Collection<TO?> {
        val result: MutableCollection<TO?> = ArrayList()
        for (from in fromList) {
            val item = apply(from)
            result.add(item)
        }
        return result
    }

    @Throws(Exception::class)
    override fun apply(from: FROM): TO? {
        return null
    }
}