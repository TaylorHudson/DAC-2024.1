package br.edu.ifpb.dac.ecommerce.model.entity.enumeration;

public enum UserType {

    CUSTOMER(1),
    OPERATOR(2);

    public int code;

    UserType(int code) {
        this.code = code;
    }

    public static UserType findByCode(int code) {
        var types = UserType.values();
        for (UserType type: types) {
            if (type.code == code)
                return type;
        }
        throw new RuntimeException("Código inválido: ".concat(String.valueOf(code)));
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
