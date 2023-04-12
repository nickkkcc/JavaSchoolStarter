package com.digdes.school.models.enums;

 public enum OperatorType {
    EQ {
        @Override
        public String toString() {
            return "=";
        }
    }, NOT_EQ {
        @Override
        public String toString() {
            return "!=";
        }
    }, GREAT_THAN_EQ {
        @Override
        public String toString() {
            return ">=";
        }
    }, LESS_THAT_EQ {
        @Override
        public String toString() {
            return "<=";
        }
    }, LESS {
        @Override
        public String toString() {
            return "<";
        }
    }, GREATER {
        @Override
        public String toString() {
            return ">";
        }
    }, LIKE {
        @Override
        public String toString() {
            return "like";
        }
    }, ILIKE {
        @Override
        public String toString() {
            return "ilike";
        }
    }
}
