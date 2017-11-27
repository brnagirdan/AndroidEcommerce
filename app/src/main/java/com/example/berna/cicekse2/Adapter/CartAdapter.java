package com.example.berna.cicekse2.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.berna.cicekse2.CartActivity;
import com.example.berna.cicekse2.R;
import com.example.berna.cicekse2.cartData.CartProductContract;

import java.text.DecimalFormat;

/**
 * Created by Berna on 11.10.2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    int productQuantity; // global because I use it  on increment/decrement functions
    /**
     * Constructor for the CustomCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public CartAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.product_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(CartProductContract.ProductEntry._CARTID);
        int productName = mCursor.getColumnIndex(CartProductContract.ProductEntry.COLUMN_CART_NAME);
        int image = mCursor.getColumnIndex(CartProductContract.ProductEntry.COLUMN_CART_IMAGE);
        int quantity = mCursor.getColumnIndex(CartProductContract.ProductEntry.COLUMN_CART_QUANTITY);
        int price = mCursor.getColumnIndex(CartProductContract.ProductEntry.COLUMN_CART_PRICE);


        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String name = mCursor.getString(productName);
        String productImage = mCursor.getString(image);
        productQuantity = mCursor.getInt(quantity);
        Double productPrice = mCursor.getDouble(price);

        DecimalFormat precision = new DecimalFormat("0.00");


        //Set values
        holder.itemView.setTag(id);
        Log.e("idddddd",String.valueOf(holder.itemView));
        holder.prodName.setText(name);
        holder.prodQuantity.setText(String.valueOf(productQuantity));
        holder.prodPrice.setText( precision.format(productPrice) + " TL");

        Glide.with(mContext)
                .load(productImage)
                .into(holder.image);

    }


    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
         /*   Intent intent = new Intent(mContext, CartActivity.class);
            mContext.startActivity(intent); */
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
            if(getItemCount()==0){
                Intent intent = new Intent(mContext, CartActivity.class);
                mContext.startActivity(intent);
            }
        }
        return temp;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView prodName, prodQuantity, prodPrice;
        ImageView image;
        ImageButton trash;
        public CartViewHolder(final View itemView) {
            super(itemView);

            trash=(ImageButton)itemView.findViewById(R.id.trash);
            prodName = (TextView) itemView.findViewById(R.id.productName);
            prodQuantity = (TextView) itemView.findViewById(R.id.quantity_text_view);
            prodPrice = (TextView) itemView.findViewById(R.id.price);
            image = (ImageView) itemView.findViewById(R.id.productImage);


            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("idddddd",String.valueOf(itemView.getTag()));
                    int id = (int)itemView.getTag();

                    // Build appropriate uri with String row id appended
                    String stringId = Integer.toString(id);
                    Uri uri = CartProductContract.ProductEntry.CONTENT_URI_CART;
                    uri = uri.buildUpon().appendPath(stringId).build();

                    // COMPLETED (2) Delete a single row of data using a ContentResolver
                    mContext.getContentResolver().delete(uri, null, null);
                }
            });
            Button increment=(Button)itemView.findViewById(R.id.increment_button);
            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // price ı ve sayıyı güncelle

                    ContentValues cartValues = new ContentValues();
                    cartValues.put(CartProductContract.ProductEntry.COLUMN_CART_QUANTITY,productQuantity +1);
                    int id = (int)itemView.getTag();

                    // Build appropriate uri with String row id appended
                    String stringId = Integer.toString(id);
                    Uri uri = CartProductContract.ProductEntry.CONTENT_URI_CART;
                    uri = uri.buildUpon().appendPath(stringId).build();

                    // COMPLETED (2) Update a single row of data using a ContentResolver
                    mContext.getContentResolver().update(uri,cartValues, null, null);
                }
            });

            /* declare quantity decrement button */
            Button decrement=(Button)itemView.findViewById(R.id.decrement_button);
            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(productQuantity>0){
                        ContentValues cartValues = new ContentValues();
                        cartValues.put(CartProductContract.ProductEntry.COLUMN_CART_QUANTITY,productQuantity-1);
                        int id = (int)itemView.getTag();

                        // Build appropriate uri with String row id appended
                        String stringId = Integer.toString(id);
                        Uri uri = CartProductContract.ProductEntry.CONTENT_URI_CART;
                        uri = uri.buildUpon().appendPath(stringId).build();

                        // COMPLETED (2) Update a single row of data using a ContentResolver
                        mContext.getContentResolver().update(uri,cartValues, null, null);
                    }
                }
            });
        }


    }




}
