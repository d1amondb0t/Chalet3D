package ca.ulaval.glo2004.domaine.memento;

import java.io.*;
import java.util.Stack;

public class Memento {
    private Stack<Object> undoStack;
    private Stack<Object> redoStack;

    public Memento() {
        undoStack = new Stack<Object>();
        redoStack = new Stack<Object>();
        undoStack.ensureCapacity(9999);
        redoStack.ensureCapacity(9999);
    }

    public void printUndoStack() {
        System.out.println("Undo Stack:");
        for (Object state : undoStack) {
            System.out.println(state);
        }
    }

    public static Object deepCopy(Object oldObj) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(oldObj);
            oos.flush();

            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            Object copy = ois.readObject();
            return copy;
        } catch (Exception e) {
            throw new RuntimeException("Exception in ObjectCloner", e);
        } finally {
            try {
                if (oos != null) oos.close();
                if (ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException("Exception while closing streams", e);
            }
        }
   }

    public void pushUndo(Object obj) {
        try {
            undoStack.push(Memento.deepCopy(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushRedo(Object obj) {
        try {
            redoStack.push(Memento.deepCopy(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object popUndo() {
        Object obj = null;
        if (!undoStack.isEmpty()) {
            obj = undoStack.pop();
        }
        return obj;
    }

    public Object popRedo() {
        Object obj = null;
        if (!redoStack.isEmpty()) {
            obj = redoStack.pop();
//          try {
//             undoStack.push(MementoCabanon.deepCopy(obj));
//          } catch (Exception e) {
//             e.printStackTrace();
//          }
        }
        return obj;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public Stack get_undoStack(){
        return this.undoStack;
    }

    public Stack get_redoStack(){
        return this.redoStack;
    }

    public void clearStack() {
        undoStack.clear();
        redoStack.clear();
    }
}