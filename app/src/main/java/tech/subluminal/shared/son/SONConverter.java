package tech.subluminal.shared.son;

@FunctionalInterface
public interface SONConverter<T> {
    T convert(SON son) throws SONRepresentationError;
}
