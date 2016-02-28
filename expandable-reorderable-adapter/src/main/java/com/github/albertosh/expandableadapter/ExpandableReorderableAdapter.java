package com.github.albertosh.expandableadapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Expandable version of RecyclerView.Adapter
 * @param <PViewHolder> Class for parent item
 * @param <CViewHolder> Class for child item
 */
public abstract class ExpandableReorderableAdapter
        <PViewHolder extends ParentViewHolder,
                CViewHolder extends ChildViewHolder>
        extends RecyclerView.Adapter implements IExpandOrCollapseListener {

    static final int TYPE_PARENT = 0;
    static final int TYPE_CHILD = 1;

    private final List<IShowableItem> viewItems;
    private ItemTouchHelper reorderableTouchHelper;

    /**
     * Constructor for just expandable behaviour
     * @param modelItems Model
     */
    public ExpandableReorderableAdapter(List<? extends IParentItem> modelItems) {
        this(modelItems, null);
    }

    /**
     * Constructor for expandable and reorderable behaviour
     * Functionally equivalent to:
     * <code>
     *     RecyclerView recycler = ...
     *     RecyclerView.Adapter adapter = new ExpandableReorderableAdapter(model);
     *     Reorderable.addReorderableBehaviour(recycler, adapter);
     * </code>
     * @param modelItems Model
     */
    public ExpandableReorderableAdapter(List<? extends IParentItem> modelItems, RecyclerView recyclerView) {
        this.viewItems = new ArrayList<>();

        for (final IParentItem item : modelItems) {
            this.viewItems.add(item);
            if (item.isOpen()) {
                this.viewItems.addAll(item.getChildren());
            }
        }

        if (recyclerView != null) {
            Reorderable.addReorderableBehaviour(recyclerView, this);
        }
    }

    /**
     * Creates a parent viewholder
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @return Parent ViewHolder
     */
    protected abstract PViewHolder onCreateParentViewHolder(ViewGroup parent);

    /**
     * Equivalent for onCreateViewHolder for Child items
     * @see RecyclerView.Adapter#onCreateViewHolder
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @return Child ViewHolder
     */
    protected abstract CViewHolder onCreateChildViewHolder(ViewGroup parent);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PARENT:
                ParentViewHolder vh = onCreateParentViewHolder(parent);
                vh.setListener(this);
                return vh;
            case TYPE_CHILD:
                return onCreateChildViewHolder(parent);
            default:
                throw new IllegalArgumentException("ViewType " + viewType + " not supported");
        }
    }

    @Override
    public int getItemViewType(int position) {
        IShowableItem item = viewItems.get(position);
        if (item instanceof IParentItem)
            return TYPE_PARENT;
        else if (item instanceof IChildItem)
            return TYPE_CHILD;
        else
            throw new IllegalArgumentException("Found unknown item at position " + position + ": " + item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_PARENT:
                bindParentViewHolder((PViewHolder) holder, (IParentItem) viewItems.get(position));
                break;
            case TYPE_CHILD:
                bindChildViewHolder((CViewHolder) holder, (IChildItem) viewItems.get(position));
                break;
        }
    }

    private void bindParentViewHolder(PViewHolder holder, IParentItem showableItem) {
        holder.bind(showableItem);
    }

    private void bindChildViewHolder(CViewHolder holder, IChildItem showableItem) {
        holder.bind(showableItem);
    }

    @Override
    public int getItemCount() {
        return viewItems.size();
    }

    @Override
    public void expand(IParentItem item) {
        if (!item.isOpen()) {
            item.toggleOpen();
            int position = viewItems.indexOf(item);
            viewItems.addAll(position + 1, item.getChildren());
            notifyItemRangeInserted(position + 1, item.getChildren().size());
        }
    }

    @Override
    public void collapse(IParentItem item) {
        if (item.isOpen()) {
            item.toggleOpen();
            int position = viewItems.indexOf(item);
            viewItems.removeAll(item.getChildren());
            notifyItemRangeRemoved(position + 1, item.getChildren().size());
        }
    }

    ItemTouchHelper getReorderableTouchHelper() {
        return reorderableTouchHelper;
    }

    void setReorderableTouchHelper(ItemTouchHelper reorderableTouchHelper) {
        this.reorderableTouchHelper = reorderableTouchHelper;
    }

    List<IShowableItem> getViewModel() {
        return viewItems;
    }
}
