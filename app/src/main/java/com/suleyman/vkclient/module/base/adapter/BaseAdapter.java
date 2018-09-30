package com.suleyman.vkclient.module.base.adapter;

import android.view.*;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public abstract class BaseAdapter<T, VH extends BaseAdapter.BaseViewHolder> extends RecyclerView.Adapter<VH> {

	private ArrayList<T> items;
	protected ItemOnClickListener<T> itemOnClickListener;

	public BaseAdapter(ArrayList<T> items) {
		this.items = items;
	}

	@Override
	public VH onCreateViewHolder(ViewGroup parent, int viewType) {
		return onCreateHolder(parent, viewType);
	}

	@Override
	public void onBindViewHolder(VH holder, final int position) {
		onBind(holder, position);

		if (itemOnClickListener != null) {
			holder.itemView.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View view) {
						itemOnClickListener.onClick(items.get(position), position);
					}
				});
		}
	}

	@Override
	public int getItemCount() {
		return items != null ? items.size() : 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	public static class BaseViewHolder extends RecyclerView.ViewHolder {
		public BaseViewHolder(View view) {
			super(view);
		}
	}

	public void setItem(int i, T item, boolean isMoveToTop) {
		items.set(i, item);
		notifyItemChanged(i, item);
	}

	public void setItems(ArrayList<T> items) {
		this.items = items;
	}

	public ArrayList<T> getItems() {
		return items;
	}

	public void setItemOnClickListener(ItemOnClickListener<T> itemOnClickListener) {
		this.itemOnClickListener = itemOnClickListener;
	}

	public ItemOnClickListener getItemOnClickListener() {
		return itemOnClickListener;
	}

	public void addMoreItems(ArrayList<T> newItems) {
		int oldSize = items.size();
		items.addAll(newItems);
		int newSize  = items.size();
		if (oldSize == 0) {
			notifyDataSetChanged();
		} else {
			notifyItemRangeInserted(oldSize, newSize);
		}
	}

	public interface ItemOnClickListener<T> {
		void onClick(T item, int pos);
	}

	public abstract VH onCreateHolder(ViewGroup parent, int viewType);
	public abstract void onBind(VH holder, int pos);
}
