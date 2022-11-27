package com.example.chappar10.data;

public class Joke {
 private String setup;
    private String punchline;

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setValue(Joke body) {
        setup = body.setup;
        punchline = body.punchline;
    }
}
