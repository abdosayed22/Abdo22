package com.aradwan054.hatly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Setting_Frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Setting_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting_Frag extends Fragment {
    Button getHelp;
    Button send , updatebtn;
    EditText Complaint;
    Context context;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    DatabaseReference myRef  , Ref;
    AlertDialog help;
    Navigation c;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Setting_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting_Frag newInstance(String param1, String param2) {
        Setting_Frag fragment = new Setting_Frag();
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


        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        LayoutInflater dialog_layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.fragment_setting, container, false);
        View dialog_view = dialog_layoutInflater.inflate(R.layout.help_dialogue, container, false);
        context = view.getContext();
        updatebtn =  view.findViewById(R.id.personal_data_btn_setting);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Complaints");




        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
             update_frag slideShowFragment = new update_frag() ;
             FragmentManager manger  = getFragmentManager() ;
             manger.beginTransaction().replace(R.id.flcontent , slideShowFragment , slideShowFragment.getTag()).commit();

            }
        });



        getHelp = view.findViewById(R.id.get_help_btn_setting);
        getHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog();
            }
        });
        final ImageView image = (ImageView)view.findViewById(R.id.cust_img_setting);
        final TextView name = (TextView) view.findViewById(R.id.cust_name_setting);//al5amis
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Ref = database.getReference("Accounts");

        Ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               String simage = dataSnapshot.child("imgURI").getValue(String.class);
               String suserName = dataSnapshot.child("UserName").getValue(String.class);//al5mis


                Glide
                        .with(context)
                        .load(simage)
                        .apply(new RequestOptions().override(250, 250))
                        .into(image);//5mis
                name.setText(suserName);//al5mis

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



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

    void dialog(){
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());//TODO : check this
        android.view.View dialog_view = layoutInflaterAndroid.inflate(R.layout.help_dialogue, null);
        send = dialog_view.findViewById(R.id.dialogue_send_btn);
        Complaint = dialog_view.findViewById(R.id.dialogue_edt_complaint);
        final AlertDialog alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity())
                .setView(dialog_view)
                .create();

//                help.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Complaint.getText().toString().equals(""))
                    Complaint.setError("Please Write Your Complaint ");

                else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Complaint", Complaint.getText().toString());
                    myRef.push().setValue(map);
                    Toast.makeText(context, "We Will Respond To You Soon ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertDialogBuilderUserInput.show();
    }


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