package com.github.albertosh.expandableadapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

/**
 * Helper class for reorderable behaviour
 */
public class Reorderable {

    /**
     * Adds reorderable behaviour
     * @param recyclerView
     * @param adapter
     */
    public static void addReorderableBehaviour(RecyclerView recyclerView, ExpandableReorderableAdapter adapter) {
        ItemTouchHelper helper = adapter.getReorderableTouchHelper();
        if (helper == null) {
            ItemTouchHelper.Callback callback =
                    new ReorderableCallback(adapter, adapter.getViewModel());
            helper = new ItemTouchHelper(callback);
            adapter.setReorderableTouchHelper(helper);
        }
        helper.attachToRecyclerView(recyclerView);
    }

    /**
     * Removes reorderable behaviour
     * @param recyclerView
     * @param adapter
     */
    public static void removeReorderableBehaviour(RecyclerView recyclerView, ExpandableReorderableAdapter adapter) {
        ItemTouchHelper touchHelper = adapter.getReorderableTouchHelper();
        touchHelper.attachToRecyclerView(null);
    }

    /**
     * Callback class for reorder events
     */
    static class ReorderableCallback extends ItemTouchHelper.Callback {

        private final RecyclerView.Adapter adapter;
        private final List<IShowableItem> viewModel;

        private ReorderableCallback(RecyclerView.Adapter adapter, List<IShowableItem> viewModel) {
            this.adapter = adapter;
            this.viewModel = viewModel;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {}

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                if (viewHolder instanceof ReorderableViewHolder) {
                    ReorderableViewHolder itemViewHolder =
                            (ReorderableViewHolder) viewHolder;
                    itemViewHolder.mOnItemSelected();
                }
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);

            if (viewHolder instanceof ReorderableViewHolder) {
                ReorderableViewHolder itemViewHolder =
                        (ReorderableViewHolder) viewHolder;
                itemViewHolder.mOnItemClear();
            }
        }


        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (viewHolder instanceof ChildViewHolder)
                return onMoveChild((ChildViewHolder)viewHolder, target);
            else
                return onMoveParent((ParentViewHolder) viewHolder, target);
        }

        private boolean onMoveChild(ChildViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (target instanceof ChildViewHolder)
                return onMoveChildToChild(viewHolder, (ChildViewHolder) target);
            else
                return false;
        }

        private boolean onMoveParent(ParentViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            if (from < to)
                return onMoveParentFromTopToBottom(viewHolder, target);
            else
                return onMoveParentFromBottomToTop(viewHolder, target);
        }

        private boolean onMoveParentFromTopToBottom(ParentViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (target instanceof ParentViewHolder) {
                return onMoveParentToParentFromTopToBottom(viewHolder, (ParentViewHolder) target);
            } else {
                return onMoveParentToChildFromTopToBottom(viewHolder, (ChildViewHolder) target);
            }
        }

        private boolean onMoveParentToChildFromTopToBottom(ParentViewHolder viewHolder, ChildViewHolder target) {
            IChildItem child = (IChildItem) target.getItem();
            IParentItem targetParent = getParentFromChild(child);

            List<IChildItem> children = targetParent.getChildren();
            int childIndex = children.indexOf(child);

            if (childIndex == children.size() - 1) {
                moveToNewPosition(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            } else
                return false;
        }

        private boolean onMoveParentToParentFromTopToBottom(ParentViewHolder viewHolder, ParentViewHolder target) {
            IParentItem toItem = (IParentItem) target.getItem();
            if (!toItem.getChildren().isEmpty() && toItem.isOpen()) {
                return false;
            } else {
                moveToNewPosition(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
        }

        private boolean onMoveParentFromBottomToTop(ParentViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (target instanceof ParentViewHolder) {
                moveToNewPosition(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            } else {
                return false;
            }
        }

        private boolean onMoveChildToChild(ChildViewHolder viewHolder, ChildViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                return onMoveChildFromTopToBottom((IChildItem) viewHolder.getItem(), fromPosition, toPosition);
            } else {
                return onMoveChildFromBottomToTop((IChildItem) viewHolder.getItem(), fromPosition, toPosition);
            }
        }

        private boolean onMoveChildFromTopToBottom(IChildItem child, int fromPosition, int toPosition) {
            boolean moved = false;
            IParentItem parent = getParentFromChild(child);
            for (int i = fromPosition; i < toPosition; i++) {
                IShowableItem lowerItem = viewModel.get(i + 1);
                if (lowerItem instanceof IChildItem) {
                    IParentItem lowerParent = getParentFromChild((IChildItem) lowerItem);
                    if (parent == lowerParent) {
                        moveToNewPosition(i, i+1);
                        moved = true;
                    }
                }
            }
            return moved;
        }

        private boolean onMoveChildFromBottomToTop(IChildItem child, int fromPosition, int toPosition) {
            boolean moved = false;
            IParentItem parent = getParentFromChild(child);
            for (int i = fromPosition; i > toPosition; i--) {
                IShowableItem lowerItem = viewModel.get(i - 1);
                if (lowerItem instanceof IChildItem) {
                    IParentItem lowerParent = getParentFromChild((IChildItem) lowerItem);
                    if (parent == lowerParent) {
                        moveToNewPosition(i, i - 1);
                        moved = true;
                    }
                }
            }
            return moved;
        }

        private void moveToNewPosition(int from, int to) {
            IShowableItem item = viewModel.get(from);
            viewModel.remove(from);
            viewModel.add(to, item);
            adapter.notifyItemMoved(from, to);
        }

        private IParentItem getParentFromChild(IChildItem child) {
            for (int index = viewModel.indexOf(child); index >= 0; index--) {
                IShowableItem item = viewModel.get(index);
                if (item instanceof IParentItem)
                    return (IParentItem) item;
            }
            throw new IllegalStateException("Parent from child " + child + " not found!");
        }

    }
}
