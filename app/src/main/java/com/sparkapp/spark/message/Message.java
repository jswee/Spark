package com.sparkapp.spark.message;

public class Message implements Comparable<Message> {

    public Long timestamp;
    public String text;

    public Message(long timestamp, String text) {
        this.timestamp = timestamp;
        this.text = text;
    }

    public int compareTo(Message other) {
        return timestamp.compareTo(other.timestamp);
    }
}
