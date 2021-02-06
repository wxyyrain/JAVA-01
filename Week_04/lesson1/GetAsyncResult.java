package org.geek_time.java_01_project.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;


public class GetAsyncResult {


    public static void main(String[] args) throws Exception {
        GetAsyncResult getAsyncResult = new GetAsyncResult();
        System.out.println(getAsyncResult.method14().getResult());
    }

    /**
     * 使用join
     *
     * @return
     * @throws InterruptedException
     */
    private Result method1() throws InterruptedException {
        Result result = new Result();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.setResult("everything will be fine");
        });
        thread.start();
        thread.join();
        return result;
    }

    /**
     * 使用wait() notify()
     *
     * @return
     * @throws InterruptedException
     */
    private Result method2() throws InterruptedException {
        Result result = new Result();
        Object lock = new Object();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.setResult("everything will be fine");
            synchronized (lock) {
                lock.notify();
            }
        });
        thread.start();
        synchronized (lock) {
            lock.wait();
        }
        return result;
    }

    /**
     * 使用thread.isAlive()
     *
     * @return
     */
    private Result method3() {
        Result result = new Result();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.setResult("everything will be fine");
        });
        thread.start();
        while (thread.isAlive()) ;
        return result;
    }

    /**
     * 使用CountDownLatch
     *
     * @return
     */
    private Result method4() throws InterruptedException {
        Result result = new Result();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                result.setResult("everything will be fine");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        });
        thread.start();
        countDownLatch.await();
        return result;
    }

    /**
     * 使用Condition的await/signal
     *
     * @return
     */
    private Result method5() throws InterruptedException {
        Result result = new Result();
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                result.setResult("everything will be fine");
                lock.lock();
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread.start();
        lock.lock();
        condition.await();
        lock.unlock();
        return result;
    }

    /**
     * 使用线程池的isTerminated
     *
     * @return
     */
    private Result method6() {
        Result result = new Result();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                Thread.sleep(1000);
                result.setResult("everything will be fine");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        return result;
    }

    /**
     * 使用线程池的awaitTermination
     *
     * @return
     * @throws InterruptedException
     */
    private Result method7() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Result result = new Result();
        executorService.execute(() -> {
            try {
                Thread.sleep(1000);
                result.setResult("everything will be fine");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        return result;
    }

    /**
     * 使用LockSupport
     *
     * @return
     */
    private Result method8() {
        Result result = new Result();
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                result.setResult("everything will be fine");
                LockSupport.unpark(mainThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        LockSupport.park();
        return result;
    }

    /**
     * 记录一个信号量，使用volatile标记
     *
     * @return
     */
    private Result method9() {
        Result result = new Result();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                result.setResult("everything will be fine");
                result.setReady(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        while (!result.isReady()) {
        }
        return result;
    }

    /**
     * 使用CyclicBarrier
     *
     * @return
     * @throws BrokenBarrierException
     * @throws InterruptedException
     */
    private Result method10() throws BrokenBarrierException, InterruptedException {
        Result result = new Result();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                result.setResult("everything will be fine");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        cyclicBarrier.await();
        return result;
    }

    /**
     * 使用Future
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private Result method11() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Result result = new Result();
        Future<String> r = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
                return "everything will be fine";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        });
        executorService.shutdown();
        result.setResult(r.get());
        return result;
    }

    /**
     * 使用FutureTask
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private Result method12() throws ExecutionException, InterruptedException {
        Result result = new Result();
        FutureTask<String> task = new FutureTask<>(() -> {
            Thread.sleep(1000);
            return "everything will be fine";
        });
        Thread thread = new Thread(task);
        thread.start();
        result.setResult(task.get());
        return result;
    }

    /**
     * 使用CompletableFuture
     *
     * @return
     */
    private Result method13() throws ExecutionException, InterruptedException {
        Result result = new Result();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "everything will be fine";
        });
        result.setResult(future.get());
        return result;
    }

    /**
     * 使用Future/FutureTask/CompletableFuture的isDone
     *
     * @return
     */
    private Result method14() {
        Result result = new Result();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.setResult("everything will be fine");
        });
        while (!future.isDone()) {
        }
        return result;
    }
}
