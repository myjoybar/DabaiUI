package com.joybar.dabaiui.data;

/**
 * Created by joybar on 02/12/16.
 */
public class AaIdAllContentBean {
    public String sortLetters;
    public String content;

    public AaIdAllContentBean(String content, String sortLetters) {
        this.content = content;
        this.sortLetters = sortLetters;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
