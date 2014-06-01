package org.reactfx;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public interface InterceptableEventStream<T> extends EventStream<T> {

    Guard mute();
    Guard pause();
    Guard retainLatest();
    Guard reduce(BinaryOperator<T> reduction);
    Guard tryReduce(BiFunction<T, T, ReductionResult<T>> reduction);

    default void muteWhile(Runnable r) {
        try(Guard g = mute()) { r.run(); }
    };

    default <U> U muteWhile(Supplier<U> f) {
        try(Guard g = mute()) { return f.get(); }
    }

    default void pauseWhile(Runnable r) {
        try(Guard g = pause()) { r.run(); }
    }

    default <U> U pauseWhile(Supplier<U> f) {
        try(Guard g = pause()) { return f.get(); }
    }

    default void retainLatestWhile(Runnable r) {
        try(Guard g = retainLatest()) { r.run(); }
    }

    default <U> U retainLatestWhile(Supplier<U> f) {
        try(Guard g = retainLatest()) { return f.get(); }
    }

    default void reduceWhile(BinaryOperator<T> reduction, Runnable r) {
        try(Guard g = reduce(reduction)) { r.run(); }
    }

    default <U> U reduceWhile(BinaryOperator<T> reduction, Supplier<U> f) {
        try(Guard g = reduce(reduction)) { return f.get(); }
    }

    default void tryReduceWhile(BiFunction<T, T, ReductionResult<T>> reduction, Runnable r) {
        try(Guard g = tryReduce(reduction)) { r.run(); }
    }

    default <U> U tryReduceWhile(BiFunction<T, T, ReductionResult<T>> reduction, Supplier<U> f) {
        try(Guard g = tryReduce(reduction)) { return f.get(); }
    }
}
