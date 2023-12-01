package com.webclient.pr02.controller;

import lombok.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@Slf4j
public class MemberController {



    private final WebClient webClient;



//    <기존코드>
//        @GetMapping(value = "GET", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//        public Flux<String> get() {
//            return webClient.get().uri("http://localhost:8080/api/v1/GET").retrieve().bodyToFlux(String.class);
//        }

//   <수정코드>
        @GetMapping(value = "GET", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public String get(Model model) {
           Flux<String> sseData = webClient.get().uri("http://localhost:8080/api/v1/GET").retrieve().bodyToFlux(String.class);
            ReactiveDataDriverContextVariable eventsDataDriver = new ReactiveDataDriverContextVariable(sseData, 100);
            model.addAttribute("sseData",eventsDataDriver);

           return "test";
        }



    //<기존코드>
//        @GetMapping(value = "GET/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//        public String getById(@PathVariable(value="id") Long id, Model model) {
//            Mono<String> result = getByIdAction(id);
//            model.addAttribute("results", result );
//            return "test";
//        }
        
//<수정코드>
        @GetMapping(value = "GET/{id}")
        public String sseEndpoint(@PathVariable(value="id") Long id, Model model) {
            Mono<String> result = getByIdAction(id);
            model.addAttribute("results", new ReactiveDataDriverContextVariable(result, 100));
            return "test";
        }

/* 비동기/논블럭 통신 매소드*/
        //@GetMapping(value = "GET123", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<String> get() {
            return webClient.get().uri("http://localhost:8080/api/v1/GET").retrieve().bodyToFlux(String.class);
        }


        public Mono<String> getByIdAction (Long id) {
            return webClient.get().uri("http://localhost:8080/api/v1/GET/"+id).retrieve().bodyToMono(String.class);
        }


//        //Test용 (동기 방식) --FLUX
        @GetMapping ("/1")
        public Mono<Flux<String>> getById() {
            System.out.println("post1");
            Mono<Flux<String>> m = Mono.just(get()).doOnNext(c-> System.out.println("출력확인용:"+c)) //doOnNext, flatMap => 비동기 작업 체인: 비동기 작업의 결과를 조작함.
                            .log();
            System.out.println("post2");
            return m;
        }

    //Test용 (동기 방식) --MONO
//    @GetMapping ("/1")
//    public Mono<Mono<String>> getById() {
//        System.out.println("post1");
//        Mono<Mono<String>> m = Mono.just(getById(1L)).doOnNext(c-> System.out.println("출력확인용:"+c))
//                .log();
//        System.out.println("post2");
//        return m;
//    }

        //test용 (비동기 방식) - FLUX
        @GetMapping("/2")
        Mono<Flux<String>> hello() {
            System.out.println("post1");
            Mono<Flux<String>> m = Mono.fromSupplier(()-> get())
                    .doOnNext(c-> System.out.println("출력확인용:" + c))
                    .log(); // Mono에서 작업한 결과를 return으로 넘어가기 전에 중간에 받는것...??
            System.out.println("post2");
            return m;
        }


    //test용 (비동기 방식) - MONO
//    @GetMapping("/2")
//    Mono<Flux<String>> hello() {
//        System.out.println("post1");
//        Mono<Flux<String>> m = Mono.fromSupplier(()-> get())
//                .doOnNext(c-> System.out.println("출력확인용:" + c))
//                .log(); // Mono에서 작업한 결과를 return으로 넘어가기 전에 중간에 받는것...??
//        System.out.println("post2");
//        return m;
//    }
} // end
