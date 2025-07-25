package net.seb234.dandysworld.dataholder;

public class MessageWrapper {
    public String type;
    public Object data;

    public MessageWrapper(String type, Object data) {
        this.type = type;
        this.data = data;
    }
}
