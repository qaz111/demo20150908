package com.example;

    /**
     *
     * @Description:
     * @version 创建时间：2014-2-28 下午4:36:40
     * @author 作者 E-mail: wc_zhang@calinks.com.cn
     */
    public class MoreThread {
        Object lock = new Object();
        int count = 0;

        public static void main(String[] args) {
            MoreThread mt = new MoreThread();
            new Thread(mt.new Run1("A", 0)).start();
            new Thread(mt.new Run1("B", 1)).start();
            new Thread(mt.new Run1("C", 2)).start();
        }



        public class Run1 implements Runnable{
            String name ;
            int mId;
            public Run1(String name,int id){
                this.name = name;
                this.mId = id;
            }

            @Override
            public void run() {
                for(int i = 0;i<10;i++){
                    synchronized (lock) {
                        while(true){
                            if(count % 3 == mId){
                                System.out.println(name);
                                count++;
                                lock.notifyAll();
                                break;
                            }else{
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }


    }


}
