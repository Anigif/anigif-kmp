package dk.anigif.kmp.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Functions working like [Flow.combine] but with more than 5 flows of divergent types.
 *
 * The implementation is to a great extent inspired by this answer: https://stackoverflow.com/a/67939655/2761541
 */
object ExtendedCombine {

    /**
     * Working like [Flow.combine] but with 6 flows of divergent types.
     */
    fun <T1, T2, T3, T4, T5, T6, R> extendedCombine(
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        transform: suspend (T1, T2, T3, T4, T5, T6) -> R
    ): Flow<R> = combine(
        combine(flow1, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6, ::Triple)
    ) { tripleFlow1, tripleFlow2 ->
        transform(
            tripleFlow1.first,
            tripleFlow1.second,
            tripleFlow1.third,
            tripleFlow2.first,
            tripleFlow2.second,
            tripleFlow2.third
        )
    }

    /**
     * Working like [Flow.combine] but with 7 flows of divergent types.
     */
    fun <T1, T2, T3, T4, T5, T6, T7, R> extendedCombine(
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
    ): Flow<R> = combine(
        combine(flow1, flow2, flow3, ::Triple),
        combine(flow4, flow5, ::Pair),
        combine(flow6, flow7, ::Pair),
    ) { tripleFlow1, pairFlow2, pairFlow3 ->
        transform(
            tripleFlow1.first,
            tripleFlow1.second,
            tripleFlow1.third,
            pairFlow2.first,
            pairFlow2.second,
            pairFlow3.first,
            pairFlow3.second
        )
    }

    /**
     * Working like [Flow.combine] but with 8 flows of divergent types.
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, R> extendedCombine(
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
    ): Flow<R> = combine(
        combine(flow1, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6, ::Triple),
        combine(flow7, flow8, ::Pair),
    ) { tripleFlow1, tripleFlow2, pairFlow3 ->
        transform(
            tripleFlow1.first,
            tripleFlow1.second,
            tripleFlow1.third,
            tripleFlow2.first,
            tripleFlow2.second,
            tripleFlow2.third,
            pairFlow3.first,
            pairFlow3.second
        )
    }

    /**
     * Working like [Flow.combine] but with 9 flows of divergent types.
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> extendedCombine(
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
    ): Flow<R> = combine(
        combine(flow1, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6, ::Triple),
        combine(flow7, flow8, flow9, ::Triple),
    ) { tripleFlow1, tripleFlow2, tripleFlow3 ->
        transform(
            tripleFlow1.first,
            tripleFlow1.second,
            tripleFlow1.third,
            tripleFlow2.first,
            tripleFlow2.second,
            tripleFlow2.third,
            tripleFlow3.first,
            tripleFlow3.second,
            tripleFlow3.third
        )
    }
}
