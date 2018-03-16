package tech.subliminal.shared.son;

import java.util.Optional;

public class SONList {

    public SONList set(int value, int i) {

        return this;
    }

    public SONList set(double value, int i) {

        return this;
    }

    public SONList set(boolean value, int i) {

        return this;
    }

    public SONList set(String value, int i) {

        return this;
    }

    public SONList set(SON value, int i) {

        return this;
    }

    public SONList set(SONList value, int i) {

        return this;
    }

    public Optional<Integer> getInt(int i) {

        return Optional.empty();
    }

    public Optional<Double> getDouble(int i) {

        return Optional.empty();
    }

    public Optional<Boolean> getBoolean(int i) {

        return Optional.empty();
    }

    public Optional<String> getString(int i) {

        return Optional.empty();
    }

    public Optional<SON> getObject(int i) {

        return Optional.empty();
    }

    public Optional<SONList> getList(int i) {

        return Optional.empty();
    }
}
