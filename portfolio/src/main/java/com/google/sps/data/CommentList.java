package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

public class CommentList{

    private final List<String> history = new ArrayList<>();

    public void addCommentEntry(String comment){
        history.add(comment);
    }

}