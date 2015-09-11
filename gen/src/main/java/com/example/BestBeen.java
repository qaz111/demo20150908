package com.example;

import java.io.Serializable;
import java.util.List;

/*{
 "Message": "操作成功",
 "State": 0,
 "data": [
 {
 "Cashcoupon": 0,
 "Integral": 0,
 "Terminalid": "5BC2E663-FAAA-4A89-8F41-9187E43AF2CD",
 "Timestamp": 0,
 "electronic": 0,
 "userid": "1278A136-82FD-40D6-BA3C-A8EE9A3D7011",
 "userroot": 0,
 "vehicleid": "7056CF3C-81BD-4BDF-8D08-BD51A08D8B7D"
 }
 ],
 "dataCount": 1
 }*/
public abstract class BestBeen  implements Serializable{

    private String Message;
    private int State;
    private int dataCount;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }


    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public abstract List getData();
}
