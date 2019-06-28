package com.aradwan054.hatly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.wearable.DataMap.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile_Frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
        Context context ;
    String suserName ;
    String semail ;
    String sphone ;
    String saddress ;
    String snational ;
    String simage ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public Profile_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_Frag newInstance(String param1, String param2) {
        Profile_Frag fragment = new Profile_Frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        context = container.getContext();


        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_profile, container, false);

        final TextView email = (TextView) view.findViewById(R.id.cust_email_profile);
        final TextView address = (TextView) view.findViewById(R.id.cust_address_profile);
        final TextView phone = (TextView) view.findViewById(R.id.cust_phone_profile);
        final TextView national = (TextView) view.findViewById(R.id.cust_national_profile);
        final TextView name = (TextView) view.findViewById(R.id.cust_name_profile);
        final ImageView image = (ImageView)view.findViewById(R.id.cust_img_profile);



         // TextView email = container.findViewById(R.id.cust_email_profile) ;
        // TextView address = container.findViewById(R.id.cust_address_profile) ;
        // TextView phone = container.findViewById(R.id.cust_phone_profile) ;
        // TextView national = container.findViewById(R.id.cust_national_profile) ;
        // TextView name = container.findViewById(R.id.cust_name_profile) ;




        FirebaseDatabase database;
        FirebaseAuth mAuth;
        DatabaseReference myRef;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef = database.getReference("Accounts");



        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 suserName = dataSnapshot.child("UserName").getValue(String.class);
                 semail = dataSnapshot.child("e-mail").getValue(String.class);
                 sphone = dataSnapshot.child("Phone").getValue(String.class);
                 saddress = dataSnapshot.child("address").getValue(String.class);
                 snational = dataSnapshot.child("national-number").getValue(String.class);
                simage = dataSnapshot.child("imgURI").getValue(String.class);




                name.setText(suserName);
                address.setText(saddress);
                phone.setText(sphone);
                national.setText(snational);
                email.setText(semail);
               // Glide.with(context).load(simage).into(image);
                Glide
                        .with(context)
                        .load(simage)
                        .apply(new RequestOptions().override(250, 250))
                        .into(image);//5mis


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });










        // Inflate the layout for this fragment

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    Intent intent = new Intent(getActivity() , Navigation.class);
                    startActivity(intent);

                    return true;

                }

                return false;
            }
        });
    }
}
