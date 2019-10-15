package com.casper.testdrivendevelopment;

import java.io.Serializable;

/**
 * Created by jszx on 2019/9/24.
 */

public class Book implements Serializable {
    private String Title;
    private int CoverResourceId;


    public Book(String title, int coverResourceId) {
        Title = title;
        CoverResourceId = coverResourceId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getCoverResourceId() {
        return CoverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        CoverResourceId = coverResourceId;
    }
}
