package com.lck;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MainTest {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final AtomicInteger atomicInteger = new AtomicInteger(1000000);

    /**
     * 创建不连续的订单号
     *
     * @param no
     *            数据中心编号
     * @return 唯一的、不连续订单号
     */
    public static synchronized String getOrderNoByUUID(String no) {
        Integer uuidHashCode = UUID.randomUUID().toString().hashCode();
        if (uuidHashCode < 0) {
            uuidHashCode = uuidHashCode * (-1);
        }
        String date = simpleDateFormat.format(new Date());
        return no + date + uuidHashCode;
    }

    /**
     * 获取同一秒钟 生成的订单号连续
     *
     * @param no
     *            数据中心编号
     * @return 同一秒内订单连续的编号
     */
    public static synchronized String getOrderNoByAtomic(String no) {
        atomicInteger.getAndIncrement();
        int i = atomicInteger.get();
        String date = simpleDateFormat.format(new Date());
        return no + date + i;
    }

    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 1000; i++) {
//            Thread.sleep(1000);
//            String orderNoByAtomic = getOrderNoByAtomic("");
//            System.out.println(orderNoByAtomic.trim());
//        }

        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
//            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }

    }
}
