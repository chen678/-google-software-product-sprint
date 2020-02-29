package com.google.sps.data;

import java.util.Date;

/** A comment entry that contains the context, time it was left, and id in the datastore. */
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