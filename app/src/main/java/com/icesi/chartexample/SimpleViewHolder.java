package com.icesi.chartexample;

import android.view.View;
import android.widget.TextView;

import de.blox.graphview.ViewHolder;

public class SimpleViewHolder extends ViewHolder {
    TextView textView;

    SimpleViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }
}
