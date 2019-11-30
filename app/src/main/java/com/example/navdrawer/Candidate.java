package com.example.navdrawer;

public class Candidate {
    String candiNm;
    String candiCnt;

    public Candidate(String candiNm, String candiCnt) {
        this.candiNm = candiNm;
        this.candiCnt = candiCnt;
    }

    public String getName() {
        return candiNm;
    }

    public void setName(String candiNm) {
        this.candiNm = candiNm;
    }

    public String getCount() {
        return candiCnt;
    }

    public void setCount(String candiCnt) {
        this.candiCnt = candiCnt;
    }
}