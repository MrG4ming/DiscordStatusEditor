package de.mrg4ming.gui.lib;

import de.mrg4ming.control.utility.IRearrangeListListener;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class DragDropList extends JList {
    public DefaultListModel model;

    public int dropTargetIndex = 0;
    public int currentIndex = 0;

    public List<IRearrangeListListener> onRearrangeList = new ArrayList<>();

    public DragDropList() {
        super(new DefaultListModel());
        this.model = (DefaultListModel) getModel();
        setDragEnabled(true);
        setDropMode(DropMode.INSERT);

        setTransferHandler(new MyListDropHandler(this));

        new MyDragListener(this);
    }
    public DragDropList(DefaultListModel model) {
        super(model);
        this.model = (DefaultListModel) getModel();
        setDragEnabled(true);
        setDropMode(DropMode.INSERT);

        setTransferHandler(new MyListDropHandler(this));

        new MyDragListener(this);
    }
}

class MyDragListener implements DragSourceListener, DragGestureListener {
    DragDropList list;

    DragSource ds = new DragSource();

    public MyDragListener(DragDropList list) {
        this.list = list;
        DragGestureRecognizer dgr = ds.createDefaultDragGestureRecognizer(list,
                DnDConstants.ACTION_MOVE, this);

    }

    public void dragGestureRecognized(DragGestureEvent dge) {
        StringSelection transferable = new StringSelection(Integer.toString(list.getSelectedIndex()));
        ds.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
    }

    public void dragEnter(DragSourceDragEvent dsde) {
        list.setValueIsAdjusting(true);
    }

    public void dragExit(DragSourceEvent dse) {
    }

    public void dragOver(DragSourceDragEvent dsde) {
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
        list.setValueIsAdjusting(false);
        if (dsde.getDropSuccess()) {
            MyListDropHandler.rearrangeList(list.currentIndex, list.dropTargetIndex, list);
            System.out.println("Drop succeeded");
        } else {
            System.out.println("Drop failed");
        }
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
    }
}

class MyListDropHandler extends TransferHandler {
    DragDropList list;

    public MyListDropHandler(DragDropList list) {
        this.list = list;
    }

    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        if (dl.getIndex() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Transferable transferable = support.getTransferable();
        String indexString;
        try {
            indexString = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return false;
        }

        int index = Integer.parseInt(indexString);
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        int dropTargetIndex = dl.getIndex();

        int selectedIndex = list.getSelectedIndex();

        //doesn't work because it is called too early while the drop isn't finished
        if(!list.getValueIsAdjusting()) {
            rearrangeList(selectedIndex, dropTargetIndex, list);
        }

        //saving data for later rearrangement of the list
        list.currentIndex = selectedIndex;
        list.dropTargetIndex = dropTargetIndex;

        //System.out.println(dropTargetIndex);
        //System.out.println("inserted");
        return true;
    }

    public static void rearrangeList(int currentIndex, int targetIndex, DragDropList list) {
        ArrayList<Object> _contents = new ArrayList<Object>();
        for(int i = 0; i < list.model.getSize(); i++) {
            _contents.add(list.getModel().getElementAt(i));
        }

        Object _item = _contents.get(currentIndex);
        _contents.remove(currentIndex);
        if(targetIndex >= list.model.size()) {
            targetIndex = list.model.size() - 1;
        }
        _contents.add(targetIndex, _item);


        list.model.clear();
        for(int i = 0; i < _contents.size(); i++) {
            list.model.add(i, _contents.get(i));
        }

        for(IRearrangeListListener l : list.onRearrangeList) {
            try {
                Object o = l.getClass().getDeclaredMethod("onRearrangeList", null).invoke(l, null);
                System.out.println(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                //e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

    }
}