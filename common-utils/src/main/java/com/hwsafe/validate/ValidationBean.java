package com.hwsafe.validate;

public class ValidationBean {
    public String fieldname;
    public String type;
    public boolean notempty;
    public String message;

    public Length length;

    public Remote remote;

    public static class Length {
        public int min;
        public int max;

        @Override
        public String toString() {
            return "Length [min=" + min + ", max=" + max + "]";
        }
    }

    public static class Remote {
        public String method;
        public String paramnames;

        @Override
        public String toString() {
            return "Remote [method=" + method + ", paramnames=" + paramnames
                    + "]";
        }
    }

    @Override
    public String toString() {
        return "ValidationBean [fieldname=" + fieldname + ", type=" + type
                + ", notempty=" + notempty + ", message=" + message
                + ", length=" + length + "]";
    }
}
