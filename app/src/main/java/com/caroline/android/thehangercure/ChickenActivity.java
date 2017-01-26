package com.caroline.android.thehangercure;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKPin;
import com.pinterest.android.pdk.PDKResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 1/25/17.
 */

public class ChickenActivity extends AppCompatActivity {

    private PDKCallback myPinsCallback;
    private PDKResponse myPinsResponse;
    private GridView gridView;
    private PinsAdapter pinAdapter;
    private boolean loading = false;
    private static final String PIN_FIELDS = "id,link,creator,image,counts,note,created_at,board,metadata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.dinner_pins );


        pinAdapter = new PinsAdapter( this );
        gridView = (GridView) findViewById( R.id.grid_view );

        gridView.setAdapter( pinAdapter );
        myPinsCallback = new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                loading = false;
                myPinsResponse = response;
                pinAdapter.setPinList( response.getPinList() );
            }

            @Override
            public void onFailure(PDKException exception) {
                loading = false;
                Log.e( getClass().getName(), exception.getDetailMessage() );
            }
        };
        loading = true;
        fetchPins();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPins();
    }

    private void fetchPins() {
        pinAdapter.setPinList( null );
        PDKClient.getInstance().getMyPins( PIN_FIELDS, myPinsCallback );
    }


    private void loadNext() {
        if (!loading && myPinsResponse.hasNext()) {
            loading = true;
            myPinsResponse.loadNext( myPinsCallback );
        }
    }

    private class PinsAdapter extends BaseAdapter {

        private List<PDKPin> _pinList;
        private Context _context;

        public PinsAdapter(Context c) {
            _context = c;
        }

        public void setPinList(List<PDKPin> list) {
            if (_pinList == null) {
                _pinList = new ArrayList<>();
            }
            if (list == null) {
                _pinList.clear();
            } else {
                _pinList.addAll( list );
            }
            notifyDataSetChanged();
        }

        public List<PDKPin> getPinList() {
            return _pinList;
        }

        @Override
        public int getCount() {
            return _pinList == null ? 0 : _pinList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderItem viewHolder;

            //load more pins if about to reach end of list
            if (_pinList.size() - position < 5) {
                loadNext();
            }

            if (convertView == null) {
                LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
                convertView = inflater.inflate( R.layout.list_item_pin, parent, false );

                viewHolder = new ViewHolderItem();
                viewHolder.textViewItem = (TextView) convertView.findViewById( R.id.title_view );
                viewHolder.imageView = (ImageView) convertView.findViewById( R.id.image_view );

                convertView.setTag( viewHolder );

            } else {
                viewHolder = (ViewHolderItem) convertView.getTag();
            }

            PDKPin pinItem = _pinList.get( position );
            if (pinItem != null) {
                viewHolder.textViewItem.setText( pinItem.getNote() );
                Picasso.with( _context.getApplicationContext() ).load( pinItem.getImageUrl() ).into( viewHolder.imageView );
            }

            return convertView;
        }

        private class ViewHolderItem {
            TextView textViewItem;
            ImageView imageView;
        }
    }
}
