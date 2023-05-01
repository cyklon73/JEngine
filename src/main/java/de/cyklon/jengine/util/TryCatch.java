package de.cyklon.jengine.util;

import java.util.function.Consumer;

@FunctionalInterface
public interface TryCatch {

    void run() throws Exception;

    static void tryCatch(TryCatch try_) {
        TryCatch.tryCatch(try_, (e) -> {});
    }

    static void tryCatch(TryCatch try_, Consumer<Exception> catch_) {
        try {
            try_.run();
        } catch (Exception e) {
            catch_.accept(e);
        }
    }

}
