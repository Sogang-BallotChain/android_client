package com.example.navdrawer;

public class Candidate {
    String candiNm;
    int candiCnt;

    public Candidate(String candiNm, int candiCnt) {
        this.candiNm = candiNm;
        this.candiCnt = candiCnt;
    }

    public String getName() {
        return candiNm;
    }

    public void setName(String candiNm) {
        this.candiNm = candiNm;
    }

    public int getCount() {
        return candiCnt;
    }

    public void setCount(int candiCnt) {
        this.candiCnt = candiCnt;
    }
}