package co.q64.emotion.lang;

import co.q64.emotion.lang.value.Value;
import co.q64.emotion.lang.value.Values;
import co.q64.emotion.lang.value.math.Matrix;
import co.q64.emotion.lang.value.special.Null;
import co.q64.emotion.lang.value.standard.ListValue;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Stack {
    protected @Inject Null nul;
    private @Getter List<Value> stack = new ArrayList<>();

    private @Getter @Setter Program program;

    protected @Inject Stack() {
    }

    public void dup(int depth) {
        for (int i = 0; i < depth; i++) {
            if (stack.size() > 0) {
                stack.add(stack.get(stack.size() - 1));
            }
        }
    }

    public void dup() {
        dup(1);
    }

    public Value pop(int depth) {
        Value result = nul;
        for (int i = 0; i < depth; i++) {
            if (stack.size() > 0) {
                result = stack.remove(stack.size() - 1);
            }
        }
        return result;
    }

    public Value pull(int depth) {
        Value result = nul;
        for (int i = 0; i < depth; i++) {
            if (stack.size() > 0) {
                Value buffer = stack.remove(stack.size() - 1);
                if (result == nul) {
                    result = buffer;
                }
            }
        }
        return result;
    }

    public Value pop() {
        return pop(1);
    }

    public void clr() {
        stack.clear();
    }

    public Value peek(int depth) {
        if (stack.size() >= depth) {
            return stack.get(stack.size() - depth);
        }
        return nul;
    }

    public Value peek() {
        return peek(1);
    }

    public Stack swap() {
        if (stack.size() > 1) {
            Value buffer = stack.get(stack.size() - 1);
            stack.set(stack.size() - 1, stack.get(stack.size() - 2));
            stack.set(stack.size() - 2, buffer);
        }
        return this;
    }

    public int size() {
        return stack.size();
    }

    public Stack push(Value value) {
        add(value);
        return this;
    }

    public Stack push(Object value) {
        if (value instanceof Value) {
            add(Values.create((Value) value));
        } else {
            add(Values.create(value.toString()));
        }
        return this;
    }

    public Stack push(List<?> values) {
        add(ListValue.of(values));
        return this;
    }

    private void add(Value value) {
        if (value.isList()) {
            boolean isMatrix = true;
            double[][] data = null;
            int rowLen = 0;
            List<Value> rows = value.iterate();
            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                Value row = rows.get(rowIndex);
                if (!row.isList()) {
                    isMatrix = false;
                    break;
                }
                List<Value> rowData = row.iterate();
                if (data == null) {
                    rowLen = rowData.size();
                    data = new double[rows.size()][rowLen];
                }
                if (rowData.size() != rowLen) {
                    isMatrix = false;
                    break;
                }
                for (int colIndex = 0; colIndex < rowData.size(); colIndex++) {
                    Value rowVal = rowData.get(colIndex);
                    if (!rowVal.isNumber()) {
                        isMatrix = false;
                        break;
                    }
                    data[rowIndex][colIndex] = rowVal.asDouble();
                }
            }
            if (isMatrix) {
                stack.add(Matrix.of(value));
                return;
            }
        }
        stack.add(value);
    }
}
