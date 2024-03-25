package com.hjss.utilities;

public class HelpText {
    String text;
    String append;
    String prepend;

    public HelpText(){
        this.text="";
        this.append="";
        this.prepend="";
    }
    public HelpText(String text, String append, String prepend) {
        this.text = text;
        this.append = append;
        this.prepend = prepend;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public String getPrepend() {
        return prepend;
    }

    public void setPrepend(String prepend) {
        this.prepend = prepend;
    }
    public String getHelpText(){
        return (this.prepend == null ? "" : this.prepend) +
                (this.text == null ? "" : this.text) +
                (this.append == null ? "" : this.append);
    }
}
