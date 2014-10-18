package com.sparkapp.spark.message;

public class Message implements Comparable<Message> {

    public Long timestamp;
    public String text;

    public Message(String str) {
        int split = str.indexOf(' ');
        timestamp = Long.parseLong(str.substring(0, split));
        text = str.substring(split);
    }

    public Message(long timestamp, String text) {
        this.timestamp = timestamp;
        this.text = text;
    }

    @Override
    public int compareTo(Message other) {
        return timestamp.compareTo(other.timestamp);
    }

    @Override
    public String toString() {
        return timestamp + " " + text + "\n";
    }
}
