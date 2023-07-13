package com.muzimin.webflux.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author: 李煌民
 * @date: 2023-07-11 17:05
 **/
public class TestReactor {
    public static void main(String[] args) {
        Flux.just(1, 2, 3, 4).subscribe(System.out::println);
        Mono.just(1);

        Flux.fromArray(new Integer[]{1, 2, 3, 4});
        Flux.fromIterable(Arrays.asList(new Integer[]{1, 2, 3, 4}));
        Flux.fromStream(Arrays.asList(new Integer[]{1, 2, 3, 4}).stream());
    }
}
