package com.umbum.pos.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CancelRepoImpl implements CancelRepo {

    @Override
    public int create(long salesId) {
        String query = "INSERT INTO CANCEL(CANCEL_ID,SALES_ID,CUSTOMER_ID,CAN_DATE)\n"
            + "VALUES(CANCEL_ID_SEQ.NEXTVAL,? ,TRUNC(SYSDATE))";

        return 0;
    }
}
