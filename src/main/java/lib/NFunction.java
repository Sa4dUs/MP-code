package lib;

import server.services.Service;

public interface NFunction<T, R> {
    T apply(R args);
}
