package com.google.sps.data;

import java.util.Date;

/** A comment entry. */
public final class Comment {

  private final long id;
  private final String context;
  private final Date date;

  public Comment(long id, String context, Date date) {
    this.id = id;
    this.context = context;
    this.date = date;
  }
}