package tech.subliminal.shared.son;

import java.util.Optional;

public class SON {

    public SON() {}

    public SON put(int value, String key, String... additionalKeys) {

        return this;
    }

    public SON put(double value, String key, String... additionalKeys) {

        return this;
    }

    public SON put(boolean value, String key, String... additionalKeys) {

        return this;
    }

    public SON put(String value, String key, String... additionalKeys) {

        return this;
    }

    public SON put(SON value, String key, String... additionalKeys) {

        return this;
    }

    public SON put(SONList value, String key, String... additionalKeys) {

        return this;
    }

    public Optional<Integer> getInt(String key, String... additionalKeys) {

        return Optional.empty();
    }

    public Optional<Double> getDouble(String key, String... additionalKeys) {

        return Optional.empty();
    }

    public Optional<Boolean> getBoolean(String key, String... additionalKeys) {

        return Optional.empty();
    }

    public Optional<String> getString(String key, String... additionalKeys) {

        return Optional.empty();
    }

    public Optional<SON> getObject(String key, String... additionalKeys) {

        return Optional.empty();
    }

    public Optional<SONList> getList(String key, String... additionalKeys) {

        return Optional.empty();
    }
}
