package lib;

import java.util.List;
import java.util.Stack;

public class RouteParser {
    public Stack<String> parse(String route) {
        Stack<String> s = new Stack<>();
        List.of(route.split("/")).forEach(s::push);
        return s;
    }
}
