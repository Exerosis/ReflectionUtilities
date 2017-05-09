package me.exerosis.reflection.beta;

public class ReflectClass<Type> {
    private Class<Type> type;

    public ReflectClass(Class<Type> type) {
        this.type = type;
    }

    public <FieldType> ReflectField<FieldType> field(ReflectClass<FieldType> type) {
        return field(type.toClass(), 0);
    }

    //Primary Methods
    public <FieldType> ReflectField<FieldType> field(ReflectClass<FieldType> type, int position) {
        return field(type.toClass(), position);
    }

    public <FieldType> ReflectField<FieldType> field(Class<FieldType> type, int position) {
        return new ReflectField<FieldType>(position);
    }

    public <FieldType> ReflectField<FieldType> field(String name) {
        return null;
    }


    public Class<Type> toClass() {
        return type;
    }


}
