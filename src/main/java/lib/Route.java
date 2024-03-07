package lib;

import java.util.List;
import java.util.Stack;

public class Route {
    private String raw;
    private Stack<String> route;

    public Route(String raw) {
        this.raw = raw;
        this.route = this.parse();
    }

    public Stack<String> parse() {
        Stack<String> s = new Stack<>();
        List.of(this.raw.split("/")).forEach(s::push);
        return s;
    }

    public String pop() {
        return route.pop();
    }
}
