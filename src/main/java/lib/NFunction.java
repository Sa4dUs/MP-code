package lib;

import server.services.Service;

public interface NFunction<T> {
    T apply(Object... args);
}
