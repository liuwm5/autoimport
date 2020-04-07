package com.liuweimin.adper.autoimport;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    @Test
    public void simpleTest1() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            completableFuture.complete(Thread.currentThread().getName());
        }).start();
        System.out.println("做你想做的其他操作");
        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void completeException() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            completableFuture.completeExceptionally(new RuntimeException("error"));
            completableFuture.complete(Thread.currentThread().getName());
        }).start();
//        doSomethingelse();//做你想做的耗时操作

        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void supplyAsync() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return Thread.currentThread().getName();
        });
//        doSomethingelse();//做你想做的耗时操作

        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
