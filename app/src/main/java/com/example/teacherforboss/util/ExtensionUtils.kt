package com.example.teacherforboss.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flowOf

inline fun <reified T> List<Flow<T>>.combineAll(): Flow<List<T>> {
    return when (size) {
        0 -> flowOf(emptyList())
        else -> combineTransform(this) { flows ->
            emit(flows.toList())
        }
    }
}
